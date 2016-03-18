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
package com.taobao.metamorphosis.gregor.master;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.TreeSet;

import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import com.taobao.gecko.service.RemotingFactory;
import com.taobao.gecko.service.RemotingServer;
import com.taobao.gecko.service.config.ServerConfig;
import com.taobao.metamorphosis.gregor.master.SamsaMasterBroker.OffsetInfo;
import com.taobao.metamorphosis.gregor.master.SamsaMasterBroker.RecoverPartition;
import com.taobao.metamorphosis.network.MetamorphosisWireFormatType;
import com.taobao.metamorphosis.network.PutCommand;
import com.taobao.metamorphosis.server.assembly.MetaMorphosisBroker;
import com.taobao.metamorphosis.server.store.AppendCallback;
import com.taobao.metamorphosis.server.store.Location;
import com.taobao.metamorphosis.server.store.MessageStore;
import com.taobao.metamorphosis.server.utils.MetaConfig;
import com.taobao.metamorphosis.utils.IdWorker;
import com.taobao.metamorphosis.utils.ZkUtils;


public class SamsaMasterBrokerUnitTest {

    private SamsaMasterBroker broker;


    @Before
    public void setUp() {
        this.broker = new SamsaMasterBroker();
    }


    @Test
    public void testFork() {
        final List<RecoverPartition> parts = new ArrayList<RecoverPartition>();
        for (int i = 0; i < 10; i++) {
            final String topic = "test" + i % 2;
            parts.add(new RecoverPartition(topic, i));
        }

        System.out.println(parts);
        final List<List<RecoverPartition>> forks = SamsaMasterBroker.fork(parts, 3);
        System.out.println(forks);
        assertEquals(3, forks.size());
        assertEquals(4, forks.get(0).size());
        assertEquals(3, forks.get(1).size());
        assertEquals(3, forks.get(2).size());

        final TreeSet<RecoverPartition> set1 = new TreeSet<SamsaMasterBroker.RecoverPartition>(parts);
        final TreeSet<RecoverPartition> set2 = new TreeSet<SamsaMasterBroker.RecoverPartition>();
        for (final List<RecoverPartition> list : forks) {
            set2.addAll(list);
        }
        assertEquals(set1, set2);
    }


    private String getDataPath() throws IOException {
        final String tempPath = System.getProperty("java.io.tmpdir");
        final String rt = tempPath + "/SamsaMasterBrokerUnitTest";
        final File dir = new File(rt);
        if (dir.exists()) {
            FileUtils.deleteDirectory(dir);
        }
        return rt;
    }

    private RemotingServer mockSlave;


    private void mockSlaveServer() throws Exception {
        final ServerConfig serverConfig = new ServerConfig();
        serverConfig.setPort(8121);
        serverConfig.setWireFormatType(new MetamorphosisWireFormatType());
        this.mockSlave = RemotingFactory.bind(serverConfig);
    }


    private void stopMockSlave() throws Exception {
        if (this.mockSlave != null) {
            this.mockSlave.stop();
        }
    }

    static class MessageInfo {
        long msgId;
        long offset;
        int partition;


        public MessageInfo(final long msgId, final long offset, final int partition) {
            super();
            this.msgId = msgId;
            this.offset = offset;
            this.partition = partition;
        }


        @Override
        public String toString() {
            return "MessageInfo [msgId=" + this.msgId + ", offset=" + this.offset + ", partition=" + this.partition
                    + "]";
        }

    }


    @Test
    public void testRecover() throws Exception {
        this.mockSlaveServer();
        final MetaConfig metaConfig = new MetaConfig();
        metaConfig.setDataPath(this.getDataPath());
        metaConfig.setDiamondZKDataId("metamorphosis.testZkConfig");
        final String topic = "SamsaMasterBrokerUnitTest";
        metaConfig.getTopics().add(topic);
        metaConfig.setNumPartitions(5);
        metaConfig.setMaxSegmentSize(1024 * 1024);
        final MetaMorphosisBroker metaBroker = new MetaMorphosisBroker(metaConfig);

        final IdWorker idWorker = metaBroker.getIdWorker();
        final byte[] data = new byte[1024];
        // ��Ҫ��recover��offset��Ϣ
        final List<MessageInfo> allMsgs = new ArrayList<MessageInfo>();
        final Random random = new Random();
        // ��������
        for (int i = 0; i < 20000; i++) {
            // ��5����������Ϊ��
            final int partition = i % 4;
            final int step = i;
            final MessageStore store = metaBroker.getStoreManager().getOrCreateMessageStore(topic, partition);
            final long msgId = idWorker.nextId();
            store.append(msgId, new PutCommand(topic, partition, data, null, 0, 0), new AppendCallback() {

                @Override
                public void appendComplete(final Location location) {
                    // �������1044����Ϊlocation��offset��������Ϣ�����
                    allMsgs.add(new MessageInfo(msgId, location.getOffset() + 1044, partition));
                }
            });
            store.flush();
        }
        // ��������Ϣ�����ѡ��20��
        final List<MessageInfo> offsetInfos = new ArrayList<SamsaMasterBrokerUnitTest.MessageInfo>();
        for (int i = 0; i < 20; i++) {
            offsetInfos.add(allMsgs.get(random.nextInt(allMsgs.size())));
        }

        // ģ�ⶩ��������offset���Ա��ƫ
        this.mockConsumersOffset(topic, metaBroker, offsetInfos);
        System.out.println("Add messages done");
        try {
            final Properties props = new Properties();
            props.setProperty("recoverOffset", "true");
            props.setProperty("slave", "localhost:8121");
            props.setProperty("recoverParallel", "false");
            assertTrue(metaBroker.getBrokerZooKeeper().getZkConfig().zkEnable);
            this.broker.init(metaBroker, props);
            // recover����ʱ�Ȳ�������zk
            assertFalse(metaBroker.getBrokerZooKeeper().getZkConfig().zkEnable);

            // ������meta broker
            metaBroker.start();
            // ��ʼrecover
            this.broker.start();

            // ȷ���Ƿ�ȫ����ƫ�ɹ�
            final String consumerId = "SamsaMasterBrokerUnitTest";
            final int brokerId = metaBroker.getMetaConfig().getBrokerId();
            // ����consumer�ڵ���Ϣ���Ա�recover
            final String consumersPath = metaBroker.getBrokerZooKeeper().getMetaZookeeper().consumersPath;
            final ZkClient zkClient = metaBroker.getBrokerZooKeeper().getZkClient();
            int consumerIdCounter = 0;
            for (final MessageInfo msgInfo : offsetInfos) {
                final int consumerIndex = consumerIdCounter++;
                final String offsetPath =
                        consumersPath + "/" + consumerId + consumerIndex + "/offsets/" + topic + "/" + brokerId + "-"
                                + msgInfo.partition;
                assertTrue(zkClient.exists(offsetPath));
                final String dataStr = ZkUtils.readDataMaybeNull(zkClient, offsetPath);
                assertNotNull(dataStr);
                final OffsetInfo offsetInfo = SamsaMasterBroker.readOffsetInfo(offsetPath, dataStr);
                System.out.println(msgInfo + "    " + dataStr);
                assertEquals(msgInfo.msgId, offsetInfo.msgId);
                assertEquals(msgInfo.offset, offsetInfo.offset);
            }
            // ȷ�ϵ��������ƫ��0
            final String offsetPath = consumersPath + "/" + consumerId + "/offsets/" + topic + "/" + brokerId + "-" + 4;
            assertTrue(zkClient.exists(offsetPath));
            final String dataStr = ZkUtils.readDataMaybeNull(zkClient, offsetPath);
            assertNotNull(dataStr);
            final OffsetInfo offsetInfo = SamsaMasterBroker.readOffsetInfo(offsetPath, dataStr);
            assertEquals(-1, offsetInfo.msgId);
            assertEquals(0, offsetInfo.offset);
        }
        finally {
            if (metaBroker != null) {
                metaBroker.stop();
            }
            this.broker.stop();
            this.stopMockSlave();
        }

    }


    private void mockConsumersOffset(final String topic, final MetaMorphosisBroker metaBroker,
            final List<MessageInfo> offsetInfos) throws Exception {
        final String consumerId = "SamsaMasterBrokerUnitTest";
        final int brokerId = metaBroker.getMetaConfig().getBrokerId();
        // ����consumer�ڵ���Ϣ���Ա�recover
        final String consumersPath = metaBroker.getBrokerZooKeeper().getMetaZookeeper().consumersPath;
        final Random rand = new Random();
        final ZkClient zkClient = metaBroker.getBrokerZooKeeper().getZkClient();
        ZkUtils.deletePathRecursive(zkClient, consumersPath);
        int consumerIdCounter = 0;
        for (final MessageInfo msgInfo : offsetInfos) {
            final int consumerIndex = consumerIdCounter++;
            final String offsetPath =
                    consumersPath + "/" + consumerId + consumerIndex + "/offsets/" + topic + "/" + brokerId + "-"
                            + msgInfo.partition;
            System.out.println(offsetPath);
            ZkUtils.makeSurePersistentPathExists(zkClient, offsetPath);
            // �������msgId����offset
            if (rand.nextBoolean()) {
                ZkUtils.updatePersistentPath(zkClient, offsetPath, msgInfo.msgId + 1 + "-" + msgInfo.offset);
            }
            else {
                ZkUtils.updatePersistentPath(zkClient, offsetPath,
                    msgInfo.msgId + "-" + (msgInfo.offset + rand.nextInt(1000)));
            }
        }
        // ��5������Ҳ����ƫ�����������᲻���ƫ��0
        final String offsetPath = consumersPath + "/" + consumerId + "/offsets/" + topic + "/" + brokerId + "-" + 4;
        ZkUtils.makeSurePersistentPathExists(zkClient, offsetPath);
        ZkUtils.updatePersistentPath(zkClient, offsetPath, "999-999");
    }
}