/*
 * (C) 2007-2012 Alibaba Group Holding Limited.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Authors:
 *   wuhua <wq163@163.com> 
 */
package com.taobao.metamorphosis.metaslave;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;

import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.metamorphosis.client.RemotingClientWrapper;
import com.taobao.metamorphosis.client.consumer.ConsumerConfig;
import com.taobao.metamorphosis.client.consumer.ConsumerZooKeeper;
import com.taobao.metamorphosis.client.consumer.FetchManager;
import com.taobao.metamorphosis.client.consumer.LoadBalanceStrategy;
import com.taobao.metamorphosis.client.consumer.SubscriberInfo;
import com.taobao.metamorphosis.client.consumer.storage.OffsetStorage;
import com.taobao.metamorphosis.cluster.Cluster;
import com.taobao.metamorphosis.exception.MetaClientException;
import com.taobao.metamorphosis.utils.MetaZookeeper;
import com.taobao.metamorphosis.utils.MetaZookeeper.ZKGroupDirs;
import com.taobao.metamorphosis.utils.MetaZookeeper.ZKGroupTopicDirs;
import com.taobao.metamorphosis.utils.ZkUtils.ZKConfig;


/**
 * Ϊslave������Ϣ��չ��ConsumerZooKeeper
 * 
 * @author �޻�
 * @since 2011-6-27 ����03:04:11
 */

public class SlaveConsumerZooKeeper extends ConsumerZooKeeper {

    private static final Log log = LogFactory.getLog(SlaveConsumerZooKeeper.class);

    private final int brokerId;


    public SlaveConsumerZooKeeper(final MetaZookeeper metaZookeeper, final RemotingClientWrapper remotingClient,
            final ZkClient zkClient, final ZKConfig zkConfig, final int brokerId) {
        super(metaZookeeper, remotingClient, zkClient, zkConfig);
        this.brokerId = brokerId;
    }


    @Override
    public void registerConsumer(final ConsumerConfig consumerConfig, final FetchManager fetchManager,
            final ConcurrentHashMap<String, SubscriberInfo> topicSubcriberRegistry, final OffsetStorage offsetStorage,
            final LoadBalanceStrategy loadBalanceStrategy) throws Exception {

        final FutureTask<ZKLoadRebalanceListener> task =
                new FutureTask<ZKLoadRebalanceListener>(new Callable<ZKLoadRebalanceListener>() {

                    @Override
                    public ZKLoadRebalanceListener call() throws Exception {
                        final ZKGroupDirs dirs =
                                SlaveConsumerZooKeeper.this.metaZookeeper.new ZKGroupDirs(consumerConfig.getGroup());
                        final String consumerUUID = SlaveConsumerZooKeeper.this.getConsumerUUID(consumerConfig);
                        final String consumerUUIDString = consumerConfig.getGroup() + "_" + consumerUUID;
                        final ZKLoadRebalanceListener loadBalanceListener =
                                new SlaveZKLoadRebalanceListener(fetchManager, dirs, consumerUUIDString,
                                    consumerConfig, offsetStorage, topicSubcriberRegistry, loadBalanceStrategy);
                        return SlaveConsumerZooKeeper.this.registerConsumerInternal(loadBalanceListener);
                    }

                });
        final FutureTask<ZKLoadRebalanceListener> existsTask =
                this.consumerLoadBalanceListeners.putIfAbsent(fetchManager, task);
        if (existsTask == null) {
            task.run();
        }
        else {
            throw new MetaClientException("Consumer has been already registed");
        }

    }

    // slaveֻѡȡmaster��ص�partition,�����ӵ�������Щpartition
    class SlaveZKLoadRebalanceListener extends ZKLoadRebalanceListener {

        public SlaveZKLoadRebalanceListener(final FetchManager fetchManager, final ZKGroupDirs dirs,
                final String consumerIdString, final ConsumerConfig consumerConfig, final OffsetStorage offsetStorage,
                final ConcurrentHashMap<String, SubscriberInfo> topicSubcriberRegistry,
                final LoadBalanceStrategy loadBalanceStrategy) {
            super(fetchManager, dirs, consumerIdString, consumerConfig, offsetStorage, topicSubcriberRegistry,
                loadBalanceStrategy);
        }


        @Override
        protected Map<String, List<String>> getConsumersPerTopic(final String group) throws Exception {
            // ֻ����slave����
            final List<String> topics = this.getTopics(this.consumerIdString);
            final Map<String/* topic */, List<String>/* consumerId */> rt = new HashMap<String, List<String>>();
            List<String> list;
            for (final String topic : topics) {
                list = new ArrayList<String>();
                list.add(this.consumerIdString);
                rt.put(topic, list);
            }
            return rt;
        }


        @Override
        protected Map<String, List<String>> getPartitionStringsForTopics(final Map<String, String> myConsumerPerTopicMap) {
            // ֻ����master��Ӧ��partition
            final Map<String, List<String>> ret =
                    SlaveConsumerZooKeeper.this.metaZookeeper.getPartitionStringsForTopicsFromMaster(
                        myConsumerPerTopicMap.keySet(), SlaveConsumerZooKeeper.this.brokerId);
            if (log.isDebugEnabled()) {
                log.debug("getPartitionStringsForTopics,topics:" + myConsumerPerTopicMap.keySet() + ",brokerId:"
                        + SlaveConsumerZooKeeper.this.brokerId + ",result:" + ret);
            }
            return ret;
        }


        @Override
        protected void updateFetchRunner(final Cluster cluster) throws Exception {
            super.updateFetchRunner(cluster.masterCluster());
        }


        @Override
        protected boolean checkClusterChange(final Cluster cluster) {
            // ȷ����Ⱥ�б仯�����Ҽ���Լ���Ӧ��master,���ⲻ��ص�master���˵���slaveҲ����
            return super.checkClusterChange(cluster)
                    && cluster.getMasterBroker(SlaveConsumerZooKeeper.this.brokerId) == null;
        }


        @Override
        protected boolean processPartition(final ZKGroupTopicDirs topicDirs, final String partition,
                final String topic, final String consumerThreadId) throws Exception {
            // ��owner partition,��������slave���ܶ���

            super.addPartitionTopicInfo(topicDirs, partition, topic, consumerThreadId);
            return true;
        }

    }

}