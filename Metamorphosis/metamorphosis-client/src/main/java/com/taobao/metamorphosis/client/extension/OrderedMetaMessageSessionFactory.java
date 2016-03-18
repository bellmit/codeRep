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
package com.taobao.metamorphosis.client.extension;

import com.taobao.metamorphosis.client.MetaClientConfig;
import com.taobao.metamorphosis.client.MetaMessageSessionFactory;
import com.taobao.metamorphosis.client.consumer.ConsumerConfig;
import com.taobao.metamorphosis.client.consumer.MessageConsumer;
import com.taobao.metamorphosis.client.consumer.storage.OffsetStorage;
import com.taobao.metamorphosis.client.extension.producer.ConfigPartitionsAware;
import com.taobao.metamorphosis.client.extension.producer.MessageRecoverManager;
import com.taobao.metamorphosis.client.extension.producer.OrderedLocalMessageStorageManager;
import com.taobao.metamorphosis.client.extension.producer.OrderedMessageProducer;
import com.taobao.metamorphosis.client.extension.producer.ProducerDiamondManager;
import com.taobao.metamorphosis.client.producer.MessageProducer;
import com.taobao.metamorphosis.client.producer.PartitionSelector;
import com.taobao.metamorphosis.client.producer.RoundRobinPartitionSelector;
import com.taobao.metamorphosis.exception.MetaClientException;


/**
 * <pre>
 * ��Ϣ�Ự������meta�ͻ��˵����ӿ�,�Ƽ�һ��Ӧ��ֻʹ��һ��.
 * ��Ҫ������Ϣ����(����ĳ��id)ɢ�е��̶�������Ҫ������ĳ�����ʹ��.
 * </pre>
 * 
 * @author �޻�
 * @since 2011-8-24 ����4:31:45
 */

public class OrderedMetaMessageSessionFactory extends MetaMessageSessionFactory implements OrderedMessageSessionFactory {

    protected final MessageRecoverManager localMessageStorageManager;
    protected final ProducerDiamondManager producerDiamondManager;


    public OrderedMetaMessageSessionFactory(final MetaClientConfig metaClientConfig) throws MetaClientException {
        super(metaClientConfig);
        this.localMessageStorageManager = new OrderedLocalMessageStorageManager(metaClientConfig);
        this.producerDiamondManager = new ProducerDiamondManager(metaClientConfig);
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.taobao.metamorphosis.client.MetaMessageSessionFactory#shutdown()
     */
    @Override
    public void shutdown() throws MetaClientException {
        super.shutdown();
        this.localMessageStorageManager.shutdown();
    }


    /**
     * ������Ϣ������
     * 
     * @param partitionSelector
     *            ����OrderedMessagePartitionSelector�ļ̳����֧����Ϣɢ�е��̶��������ɿ����ͺ�˳������
     */
    @Override
    public MessageProducer createProducer(final PartitionSelector partitionSelector) {
        return this.createProducer(partitionSelector, false);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.taobao.metamorphosis.client.MetaMessageSessionFactory#createProducer
     * ()
     */
    @Override
    public MessageProducer createProducer() {
        return this.createProducer(new RoundRobinPartitionSelector(), false);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.taobao.metamorphosis.client.MetaMessageSessionFactory#createProducer
     * (boolean)
     */
    @Override
    public MessageProducer createProducer(final boolean ordered) {
        return this.createProducer(new RoundRobinPartitionSelector(), ordered);
    }


    /**
     * ������Ϣ������
     * 
     * @param partitionSelector
     *            ����OrderedMessagePartitionSelector�ļ̳����֧����Ϣɢ�е��̶��������ɿ����ͺ�˳������
     * @param ordered
     *            �Ƿ���Ҫ���߳�����
     */
    @Override
    public MessageProducer createProducer(final PartitionSelector partitionSelector, final boolean ordered) {
        if (partitionSelector == null) {
            throw new IllegalArgumentException("Null partitionSelector");
        }
        if (partitionSelector instanceof ConfigPartitionsAware) {
            ((ConfigPartitionsAware) partitionSelector)
                .setConfigPartitions(this.producerDiamondManager.getPartitions());
        }
        return this.addChild(new OrderedMessageProducer(this, this.remotingClient, partitionSelector,
            this.producerZooKeeper, this.sessionIdGenerator.generateId(), this.localMessageStorageManager));
    }


    @Override
    public MessageConsumer createConsumer(final ConsumerConfig consumerConfig) {
        return super.createConsumer(consumerConfig);
    }


    @Override
    public MessageConsumer createConsumer(final ConsumerConfig consumerConfig, final OffsetStorage offsetStorage) {
        return super.createConsumer(consumerConfig, offsetStorage);
    }

}