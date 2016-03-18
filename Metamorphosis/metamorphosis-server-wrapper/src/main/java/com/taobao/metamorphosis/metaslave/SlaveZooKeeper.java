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

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.metamorphosis.cluster.Broker;
import com.taobao.metamorphosis.cluster.Partition;
import com.taobao.metamorphosis.server.assembly.MetaMorphosisBroker;
import com.taobao.metamorphosis.utils.MetaZookeeper;


/**
 * �����zk����,�����master��zk�ϵ�ע��
 * 
 * @author �޻�
 * @since 2011-6-24 ����05:46:36
 */

class SlaveZooKeeper {

    private static final Log log = LogFactory.getLog(SlaveZooKeeper.class);

    private final MetaMorphosisBroker broker;
    private final SubscribeHandler subscribeHandler;
    private final MasterBrokerIdListener masterBrokerIdListener;


    public SlaveZooKeeper(final MetaMorphosisBroker broker, final SubscribeHandler subscribeHandler) {
        this.broker = broker;
        this.subscribeHandler = subscribeHandler;
        this.masterBrokerIdListener = new MasterBrokerIdListener();
    }


    public void start() {

        // ����zk��Ϣ�仯
        this.getZkClient().subscribeDataChanges(
            this.getMetaZookeeper().brokerIdsPathOf(this.broker.getMetaConfig().getBrokerId(), -1),
            this.masterBrokerIdListener);
    }


    public String getMasterServerUrl() {
        final Broker masterBroker =
                this.getMetaZookeeper().getMasterBrokerById(this.broker.getMetaConfig().getBrokerId());
        return masterBroker != null ? masterBroker.getZKString() : null;
    }


    public Map<String, List<Partition>> getPartitionsForTopicsFromMaster() {
        return this.getMetaZookeeper().getPartitionsForTopicsFromMaster(this.getMasterTopics(),
            this.broker.getMetaConfig().getBrokerId());
    }


    private Set<String> getMasterTopics() {
        return this.getMetaZookeeper().getTopicsByBrokerIdFromMaster(this.broker.getMetaConfig().getBrokerId());
    }


    private ZkClient getZkClient() {
        return this.broker.getBrokerZooKeeper().getZkClient();
    }


    private MetaZookeeper getMetaZookeeper() {
        return this.broker.getBrokerZooKeeper().getMetaZookeeper();
    }

    private final class MasterBrokerIdListener implements IZkDataListener {

        @Override
        public void handleDataChange(final String dataPath, final Object data) throws Exception {
            // ����slave��������master������ʱ
            log.info("data changed in zk,path=" + dataPath);
            int zkSyncTimeMs;
            try {
                zkSyncTimeMs = broker.getMetaConfig().getZkConfig().zkSyncTimeMs;
            }
            catch (final Exception e) {
                zkSyncTimeMs = 5000;
                // ignore
            }
            // �ȴ�zk����ͬ���������������
            Thread.sleep(zkSyncTimeMs);
            SlaveZooKeeper.this.subscribeHandler.start();
        }


        @Override
        public void handleDataDeleted(final String dataPath) throws Exception {
            log.info("data deleted in zk,path=" + dataPath);
            // ����RemotingClientWrapper�Ļ���,close�Ĵ���Ҫ����connect�Ĵ������������رյ�����,
            // Ҫ�����ڶ���master��Ϣǰ������һ��,����Ҫ������ر�һ��
            // ���������Ӻ͹ر��и��ؾ��⸺��
            SlaveZooKeeper.this.subscribeHandler.closeConnectIfNeed();
        }
    }

}