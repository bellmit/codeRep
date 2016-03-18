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
 *   wuhua <wq163@163.com> , boyan <killme2008@gmail.com>
 */
package com.taobao.metamorphosis.client.consumer.storage;

import java.util.Collection;

import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.metamorphosis.client.ZkClientChangedListener;
import com.taobao.metamorphosis.client.consumer.TopicPartitionRegInfo;
import com.taobao.metamorphosis.cluster.Partition;
import com.taobao.metamorphosis.utils.MetaZookeeper;
import com.taobao.metamorphosis.utils.MetaZookeeper.ZKGroupTopicDirs;
import com.taobao.metamorphosis.utils.ZkUtils;


/**
 * ����zk��offset�洢��
 * 
 * @author boyan
 * @Date 2011-4-28
 * 
 */
public class ZkOffsetStorage implements OffsetStorage, ZkClientChangedListener {
    private volatile ZkClient zkClient;
    private final MetaZookeeper metaZookeeper;


    @Override
    public void onZkClientChanged(final ZkClient newClient) {
        log.info("Update ZkOffsetStorage's zkClient...");
        this.zkClient = newClient;
    }


    public ZkOffsetStorage(final MetaZookeeper metaZookeeper, final ZkClient zkClient) {
        super();
        this.metaZookeeper = metaZookeeper;
        this.zkClient = zkClient;
    }

    static final Log log = LogFactory.getLog(ZkOffsetStorage.class);


    @Override
    public void commitOffset(final String group, final Collection<TopicPartitionRegInfo> infoList) {
        if (this.zkClient == null || infoList == null || infoList.isEmpty()) {
            return;
        }
        for (final TopicPartitionRegInfo info : infoList) {
            final String topic = info.getTopic();
            final ZKGroupTopicDirs topicDirs = this.metaZookeeper.new ZKGroupTopicDirs(topic, group);
            long newOffset = -1;
            long msgId = -1;
            // ��������֤msgId��offsetһ��
            synchronized (info) {
                // ֻ�����б����
                if (!info.isModified()) {
                    continue;
                }
                newOffset = info.getOffset().get();
                msgId = info.getMessageId();
                // ������ϣ�����Ϊfalse
                info.setModified(false);
            }
            try {
                // �洢��zk�������ΪmsgId-offset
                // ԭʼֻ��offset����1.4��ʼ�޸�ΪmsgId-offset,Ϊ��ʵ��ͬ������
                ZkUtils.updatePersistentPath(this.zkClient, topicDirs.consumerOffsetDir + "/"
                        + info.getPartition().toString(), msgId + "-" + newOffset);
            }
            catch (final Throwable t) {
                log.error("exception during commitOffsets", t);
            }
            if (log.isDebugEnabled()) {
                log.debug("Committed offset " + newOffset + " for topic " + info.getTopic());
            }

        }
    }


    @Override
    public TopicPartitionRegInfo load(final String topic, final String group, final Partition partition) {
        final ZKGroupTopicDirs topicDirs = this.metaZookeeper.new ZKGroupTopicDirs(topic, group);
        final String znode = topicDirs.consumerOffsetDir + "/" + partition.toString();
        final String offsetString = ZkUtils.readDataMaybeNull(this.zkClient, znode);
        if (offsetString == null) {
            return null;
        }
        else {
            // �����Ͽͻ���
            final int index = offsetString.lastIndexOf("-");
            if (index > 0) {
                // 1.4��ʼ���¿ͻ���
                final long msgId = Long.parseLong(offsetString.substring(0, index));
                final long offset = Long.parseLong(offsetString.substring(index + 1));
                return new TopicPartitionRegInfo(topic, partition, offset, msgId);
            }
            else {
                // �Ͽͻ���
                final long offset = Long.parseLong(offsetString);
                return new TopicPartitionRegInfo(topic, partition, offset);
            }
        }
    }


    @Override
    public void close() {
        // do nothing
    }


    @Override
    public void initOffset(final String topic, final String group, final Partition partition, final long offset) {
        // do nothing
    }

}