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
package com.taobao.metamorphosis.client;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.gecko.service.RemotingFactory;
import com.taobao.gecko.service.config.ClientConfig;
import com.taobao.gecko.service.exception.NotifyRemotingException;
import com.taobao.metamorphosis.client.consumer.ConsisHashStrategy;
import com.taobao.metamorphosis.client.consumer.ConsumerConfig;
import com.taobao.metamorphosis.client.consumer.ConsumerZooKeeper;
import com.taobao.metamorphosis.client.consumer.DefaultLoadBalanceStrategy;
import com.taobao.metamorphosis.client.consumer.LoadBalanceStrategy;
import com.taobao.metamorphosis.client.consumer.MessageConsumer;
import com.taobao.metamorphosis.client.consumer.RecoverManager;
import com.taobao.metamorphosis.client.consumer.RecoverStorageManager;
import com.taobao.metamorphosis.client.consumer.SimpleMessageConsumer;
import com.taobao.metamorphosis.client.consumer.SubscribeInfoManager;
import com.taobao.metamorphosis.client.consumer.storage.OffsetStorage;
import com.taobao.metamorphosis.client.consumer.storage.ZkOffsetStorage;
import com.taobao.metamorphosis.client.producer.MessageProducer;
import com.taobao.metamorphosis.client.producer.PartitionSelector;
import com.taobao.metamorphosis.client.producer.ProducerZooKeeper;
import com.taobao.metamorphosis.client.producer.RoundRobinPartitionSelector;
import com.taobao.metamorphosis.client.producer.SimpleMessageProducer;
import com.taobao.metamorphosis.exception.InvalidConsumerConfigException;
import com.taobao.metamorphosis.exception.InvalidOffsetStorageException;
import com.taobao.metamorphosis.exception.MetaClientException;
import com.taobao.metamorphosis.exception.NetworkException;
import com.taobao.metamorphosis.network.MetamorphosisWireFormatType;
import com.taobao.metamorphosis.utils.IdGenerator;
import com.taobao.metamorphosis.utils.MetaZookeeper;
import com.taobao.metamorphosis.utils.Utils;
import com.taobao.metamorphosis.utils.ZkUtils;
import com.taobao.metamorphosis.utils.ZkUtils.ZKConfig;


/**
 * ��Ϣ�Ự���������õ����ȼ�������ʹ�ô����MetaClientConfig�е������
 * ���ʹ��MetaClientConfig�е�zkConfig���õ�zk�е�ѡ������û�У����diamond��ȡzk��ַ����ȡ������
 * 
 * @author boyan
 * @Date 2011-4-21
 * @author wuhua
 * @Date 2011-8-4
 */
public class MetaMessageSessionFactory implements MessageSessionFactory {
    protected RemotingClientWrapper remotingClient;
    private final MetaClientConfig metaClientConfig;
    private volatile ZkClient zkClient;

    static final Log log = LogFactory.getLog(MetaMessageSessionFactory.class);

    private final CopyOnWriteArrayList<ZkClientChangedListener> zkClientChangedListeners =
            new CopyOnWriteArrayList<ZkClientChangedListener>();

    protected final ProducerZooKeeper producerZooKeeper;

    private final ConsumerZooKeeper consumerZooKeeper;

    // private DiamondManager diamondManager;
    private final CopyOnWriteArrayList<Shutdownable> children = new CopyOnWriteArrayList<Shutdownable>();
    private volatile boolean shutdown;
    private volatile boolean isHutdownHookCalled = false;
    private final Thread shutdownHook;
    private ZKConfig zkConfig;
    private final RecoverManager recoverManager;
    private final SubscribeInfoManager subscribeInfoManager;

    protected final IdGenerator sessionIdGenerator;

    protected MetaZookeeper metaZookeeper;


    /**
     * ����ͨѶ�ͻ���
     * 
     * @return
     */
    public RemotingClientWrapper getRemotingClient() {
        return this.remotingClient;
    }


    /**
     * ���ض��Ĺ�ϵ������
     * 
     * @return
     */
    public SubscribeInfoManager getSubscribeInfoManager() {
        return this.subscribeInfoManager;
    }


    /**
     * ���ؿͻ�������
     * 
     * @return
     */
    public MetaClientConfig getMetaClientConfig() {
        return this.metaClientConfig;
    }


    /**
     * ���������ߺ�zk����������
     * 
     * @return
     */
    public ProducerZooKeeper getProducerZooKeeper() {
        return this.producerZooKeeper;
    }


    /**
     * ���������ߺ�zk����������
     * 
     * @return
     */
    public ConsumerZooKeeper getConsumerZooKeeper() {
        return this.consumerZooKeeper;
    }


    /**
     * ���ر��ػָ���Ϣ������
     * 
     * @return
     */
    public RecoverManager getRecoverStorageManager() {
        return this.recoverManager;
    }


    /**
     * ���ش˹��������������Ӷ����������ߡ������ߵ�
     * 
     * @return
     */
    public CopyOnWriteArrayList<Shutdownable> getChildren() {
        return this.children;
    }


    public MetaMessageSessionFactory(final MetaClientConfig metaClientConfig) throws MetaClientException {
        super();
        this.checkConfig(metaClientConfig);
        this.metaClientConfig = metaClientConfig;
        final ClientConfig clientConfig = new ClientConfig();
        clientConfig.setTcpNoDelay(false);
        clientConfig.setWireFormatType(new MetamorphosisWireFormatType());
        clientConfig.setMaxScheduleWrittenBytes(Runtime.getRuntime().maxMemory() / 3);
        try {
            this.remotingClient = new RemotingClientWrapper(RemotingFactory.connect(clientConfig));
        }
        catch (final NotifyRemotingException e) {
            throw new NetworkException("Create remoting client failed", e);
        }
        // ��������ã���ʹ�����õ�url�����ӣ�����ʹ��zk���ַ�����
        if (this.metaClientConfig.getServerUrl() != null) {
            this.connectServer(this.metaClientConfig);
        }
        else {
            this.initZooKeeper();
        }

        this.producerZooKeeper =
                new ProducerZooKeeper(this.metaZookeeper, this.remotingClient, this.zkClient, metaClientConfig);
        this.sessionIdGenerator = new IdGenerator();
        // modify by wuhua
        this.consumerZooKeeper = this.initConsumerZooKeeper(this.remotingClient, this.zkClient, this.zkConfig);
        this.zkClientChangedListeners.add(this.producerZooKeeper);
        this.zkClientChangedListeners.add(this.consumerZooKeeper);
        this.subscribeInfoManager = new SubscribeInfoManager();
        this.recoverManager = new RecoverStorageManager(this.metaClientConfig, this.subscribeInfoManager);
        this.shutdownHook = new Thread() {

            @Override
            public void run() {
                try {
                    MetaMessageSessionFactory.this.isHutdownHookCalled = true;
                    MetaMessageSessionFactory.this.shutdown();
                }
                catch (final MetaClientException e) {
                    log.error("�ر�session factoryʧ��", e);
                }
            }

        };
        Runtime.getRuntime().addShutdownHook(this.shutdownHook);
    }


    // add by wuhua
    protected ConsumerZooKeeper initConsumerZooKeeper(final RemotingClientWrapper remotingClientWrapper,
            final ZkClient zkClient2, final ZKConfig config) {
        return new ConsumerZooKeeper(this.metaZookeeper, this.remotingClient, this.zkClient, this.zkConfig);
    }


    private void checkConfig(final MetaClientConfig metaClientConfig) throws MetaClientException {
        if (metaClientConfig == null) {
            throw new MetaClientException("null configuration");
        }
    }


    private void connectServer(final MetaClientConfig metaClientConfig) throws NetworkException {
        try {
            this.remotingClient.connect(metaClientConfig.getServerUrl());
            this.remotingClient.awaitReadyInterrupt(metaClientConfig.getServerUrl());
        }
        catch (final NotifyRemotingException e) {
            throw new NetworkException("Connect to " + metaClientConfig.getServerUrl() + " failed", e);
        }
        catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    private void initZooKeeper() throws MetaClientException {
        // ����ʹ�����õ�zookeepr����δ�diamond��ȡ
        this.zkConfig = null;
        if (this.metaClientConfig.getZkConfig() != null) {
            this.zkConfig = this.metaClientConfig.getZkConfig();

        }
        else {
            this.zkConfig = this.loadZkConfigFromDiamond();

        }
        if (this.zkConfig != null) {
            this.zkClient =
                    new ZkClient(this.zkConfig.zkConnect, this.zkConfig.zkSessionTimeoutMs,
                        this.zkConfig.zkConnectionTimeoutMs, new ZkUtils.StringSerializer());
            this.metaZookeeper = new MetaZookeeper(this.zkClient, this.zkConfig.zkRoot);
        }
        else {
            throw new MetaClientException("No zk config offered");
        }
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.taobao.metamorphosis.client.SessionFactory#close()
     */
    @Override
    public void shutdown() throws MetaClientException {
        if (this.shutdown) {
            return;
        }
        this.shutdown = true;
        // if (this.diamondManager != null) {
        // this.diamondManager.close();
        // }
        this.recoverManager.shutdown();
        // this.localMessageStorageManager.shutdown();
        for (final Shutdownable child : this.children) {
            child.shutdown();
        }
        try {
            this.remotingClient.stop();
        }
        catch (final NotifyRemotingException e) {
            throw new NetworkException("Stop remoting client failed", e);
        }
        if (this.zkClient != null) {
            this.zkClient.close();
        }
        if (!this.isHutdownHookCalled) {
            Runtime.getRuntime().removeShutdownHook(this.shutdownHook);
        }

    }


    /**
     * ��ʱ��zk.properties�����
     * 
     * @return
     */
    // ��Ԫ����Ҫ��ͨ����,���޸�resources/zk.properties���zk��ַ
    private ZKConfig loadZkConfigFromDiamond() {
        // ���Դ�diamond��ȡ
        // this.diamondManager =
        // new DefaultDiamondManager(this.metaClientConfig.getDiamondZKGroup(),
        // this.metaClientConfig.getDiamondZKDataId(), new ManagerListener() {
        // @Override
        // public void receiveConfigInfo(final String configInfo) {
        // log.info("Receiving new diamond zk config:" + configInfo);
        // log.info("Closing zk client");
        // MetaMessageSessionFactory.this.zkClient.close();
        // final Properties properties = new Properties();
        // try {
        // properties.load(new StringReader(configInfo));
        // final ZKConfig zkConfig = DiamondUtils.getZkConfig(properties);
        // MetaMessageSessionFactory.this.zkClient.close();
        // Thread.sleep(zkConfig.zkSyncTimeMs);
        // log.info("Initialize zk client...");
        // final ZkClient newClient =
        // new ZkClient(zkConfig.zkConnect, zkConfig.zkSessionTimeoutMs,
        // zkConfig.zkConnectionTimeoutMs, new ZkUtils.StringSerializer());
        // log.info("Begin to notify zkClient has been changed...");
        // MetaMessageSessionFactory.this.metaZookeeper.setZkClient(newClient);
        // MetaMessageSessionFactory.this.notifyZkClientChanged(newClient);
        // MetaMessageSessionFactory.this.zkClient = newClient;
        // log.info("End notifying zkClient has been changed...");
        // }
        // catch (final Exception e) {
        // log.error("��diamond����zk����ʧ��", e);
        // }
        // }
        //
        //
        // @Override
        // public Executor getExecutor() {
        // return null;
        // }
        // });
        try {
            final Properties properties = Utils.getResourceAsProperties("zk.properties", "GBK");
            final ZKConfig zkConfig = new ZKConfig();
            if (StringUtils.isNotBlank(properties.getProperty("zk.zkConnect"))) {
                zkConfig.zkConnect = properties.getProperty("zk.zkConnect");
            }

            if (StringUtils.isNotBlank(properties.getProperty("zk.zkSessionTimeoutMs"))) {
                zkConfig.zkSessionTimeoutMs = Integer.parseInt(properties.getProperty("zk.zkSessionTimeoutMs"));
            }

            if (StringUtils.isNotBlank(properties.getProperty("zk.zkConnectionTimeoutMs"))) {
                zkConfig.zkConnectionTimeoutMs = Integer.parseInt(properties.getProperty("zk.zkConnectionTimeoutMs"));
            }

            if (StringUtils.isNotBlank(properties.getProperty("zk.zkSyncTimeMs"))) {
                zkConfig.zkSyncTimeMs = Integer.parseInt(properties.getProperty("zk.zkSyncTimeMs"));
            }

            return zkConfig;// DiamondUtils.getZkConfig(this.diamondManager,
                            // 10000);
        }
        catch (final IOException e) {
            log.error("zk����ʧ��", e);
            return null;
        }
    }


    private void notifyZkClientChanged(final ZkClient zkClient) {
        for (final ZkClientChangedListener listener : this.zkClientChangedListeners) {
            try {
                listener.onZkClientChanged(zkClient);
            }
            catch (final Throwable t) {
                log.error("����zKClientʧ��", t);
            }
        }
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.taobao.metamorphosis.client.SessionFactory#createProducer(com.taobao
     * .metamorphosis.client.producer.PartitionSelector)
     */
    @Override
    public MessageProducer createProducer(final PartitionSelector partitionSelector) {
        return this.createProducer(partitionSelector, false);
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.taobao.metamorphosis.client.SessionFactory#createProducer()
     */
    @Override
    public MessageProducer createProducer() {
        return this.createProducer(new RoundRobinPartitionSelector(), false);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.taobao.metamorphosis.client.SessionFactory#createProducer(boolean)
     */
    @Override
    @Deprecated
    public MessageProducer createProducer(final boolean ordered) {
        return this.createProducer(new RoundRobinPartitionSelector(), ordered);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.taobao.metamorphosis.client.SessionFactory#createProducer(com.taobao
     * .metamorphosis.client.producer.PartitionSelector, boolean)
     */
    @Override
    @Deprecated
    public MessageProducer createProducer(final PartitionSelector partitionSelector, final boolean ordered) {
        if (partitionSelector == null) {
            throw new IllegalArgumentException("Null partitionSelector");
        }
        return this.addChild(new SimpleMessageProducer(this, this.remotingClient, partitionSelector,
            this.producerZooKeeper, this.sessionIdGenerator.generateId()));
    }


    protected <T extends Shutdownable> T addChild(final T child) {
        this.children.add(child);
        return child;
    }


    /**
     * ɾ���ӻỰ
     * 
     * @param <T>
     * @param child
     */
    public <T extends Shutdownable> void removeChild(final T child) {
        this.children.remove(child);
    }


    private synchronized MessageConsumer createConsumer0(final ConsumerConfig consumerConfig,
            final OffsetStorage offsetStorage, final RecoverManager recoverManager0) {
        if (consumerConfig.getServerUrl() == null) {
            consumerConfig.setServerUrl(this.metaClientConfig.getServerUrl());
        }
        if (offsetStorage == null) {
            throw new InvalidOffsetStorageException("Null offset storage");
        }
        // ��Ҫʱ����recover
        if (!recoverManager0.isStarted()) {
            recoverManager0.start(this.metaClientConfig);
        }
        this.checkConsumerConfig(consumerConfig);
        return this.addChild(new SimpleMessageConsumer(this, this.remotingClient, consumerConfig,
            this.consumerZooKeeper, this.producerZooKeeper, this.subscribeInfoManager, recoverManager0, offsetStorage,
            this.createLoadBalanceStrategy(consumerConfig)));
    }


    protected LoadBalanceStrategy createLoadBalanceStrategy(final ConsumerConfig consumerConfig) {
        switch (consumerConfig.getLoadBalanceStrategyType()) {
        case DEFAULT:
            return new DefaultLoadBalanceStrategy();
        case CONSIST:
            return new ConsisHashStrategy();
        default:
            throw new IllegalArgumentException("Unknow load balance strategy type:"
                    + consumerConfig.getLoadBalanceStrategyType());
        }
    }


    protected MessageConsumer createConsumer(final ConsumerConfig consumerConfig, final OffsetStorage offsetStorage,
            final RecoverManager recoverManager0) {
        OffsetStorage offsetStorageCopy = offsetStorage;
        if (offsetStorageCopy == null) {
            offsetStorageCopy = new ZkOffsetStorage(this.metaZookeeper, this.zkClient);
            this.zkClientChangedListeners.add((ZkOffsetStorage) offsetStorageCopy);
        }

        return this.createConsumer0(consumerConfig, offsetStorageCopy, recoverManager0 != null ? recoverManager0
                : this.recoverManager);

    }


    @Override
    public MessageConsumer createConsumer(final ConsumerConfig consumerConfig, final OffsetStorage offsetStorage) {
        return this.createConsumer(consumerConfig, offsetStorage, this.recoverManager);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.taobao.metamorphosis.client.SessionFactory#createConsumer(com.taobao
     * .metamorphosis.client.consumer.ConsumerConfig)
     */
    @Override
    public MessageConsumer createConsumer(final ConsumerConfig consumerConfig) {
        // final ZkOffsetStorage offsetStorage = new
        // ZkOffsetStorage(this.zkClient);
        // this.zkClientChangedListeners.add(offsetStorage);
        // return this.createConsumer(consumerConfig, offsetStorage);
        return this.createConsumer(consumerConfig, null, null);
    }

    static final char[] INVALID_GROUP_CHAR = { '~', '!', '#', '$', '%', '^', '&', '*', '(', ')', '+', '=', '`', '\'',
                                              '"', ',', ';', '/', '?', '[', ']', '<', '>', '.', ':' };


    protected void checkConsumerConfig(final ConsumerConfig consumerConfig) {
        if (StringUtils.isBlank(consumerConfig.getGroup())) {
            throw new InvalidConsumerConfigException("Blank group");
        }
        final char[] chary = new char[consumerConfig.getGroup().length()];
        consumerConfig.getGroup().getChars(0, chary.length, chary, 0);
        for (final char ch : chary) {
            for (final char invalid : INVALID_GROUP_CHAR) {
                if (ch == invalid) {
                    throw new InvalidConsumerConfigException("Group name has invalid character " + ch);
                }
            }
        }
        if (consumerConfig.getFetchRunnerCount() <= 0) {
            throw new InvalidConsumerConfigException("Invalid fetchRunnerCount:" + consumerConfig.getFetchRunnerCount());
        }
        if (consumerConfig.getFetchTimeoutInMills() <= 0) {
            throw new InvalidConsumerConfigException("Invalid fetchTimeoutInMills:"
                    + consumerConfig.getFetchTimeoutInMills());
        }
    }

}