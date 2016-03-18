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

import com.taobao.metamorphosis.client.consumer.ConsumerConfig;
import com.taobao.metamorphosis.client.consumer.MessageConsumer;
import com.taobao.metamorphosis.client.consumer.storage.OffsetStorage;
import com.taobao.metamorphosis.client.producer.MessageProducer;
import com.taobao.metamorphosis.client.producer.PartitionSelector;
import com.taobao.metamorphosis.exception.MetaClientException;


/**
 * ��Ϣ�Ự������meta�ͻ��˵����ӿ�,�Ƽ�һ��Ӧ��ֻʹ��һ��MessageSessionFactory
 * 
 * @author boyan
 * @Date 2011-4-27
 * 
 */
public interface MessageSessionFactory extends Shutdownable {

    /**
     * �رչ���
     * 
     * @throws MetaClientException
     */
    @Override
    public void shutdown() throws MetaClientException;


    /**
     * ������Ϣ������
     * 
     * @param partitionSelector
     *            ����ѡ����
     * @return
     */
    public MessageProducer createProducer(PartitionSelector partitionSelector);


    /**
     * ������Ϣ�����ߣ�Ĭ��ʹ����ѯ����ѡ����
     * 
     * @return
     */
    public MessageProducer createProducer();


    /**
     * ������Ϣ�����ߣ�Ĭ��ʹ����ѯ����ѡ�������������Ѿ�����������ʹ�ã����ų���δ��ĳ���汾ɾ����
     * 
     * @param ordered
     *            �Ƿ�����trueΪ���������������Ϣ���շ���˳�򱣴���MQ server
     * @return
     */
    @Deprecated
    public MessageProducer createProducer(boolean ordered);


    /**
     * ������Ϣ������,�������Ѿ�����������ʹ�ã����ų���δ��ĳ���汾ɾ����
     * 
     * @param partitionSelector
     *            ����ѡ����
     * @param ordered
     *            �Ƿ�����trueΪ���������������Ϣ���շ���˳�򱣴���MQ server
     * @return
     */
    @Deprecated
    public MessageProducer createProducer(PartitionSelector partitionSelector, boolean ordered);


    /**
     * ������Ϣ�����ߣ�Ĭ�Ͻ�offset�洢��zk
     * 
     * @param consumerConfig
     *            ����������
     * @return
     */
    public MessageConsumer createConsumer(ConsumerConfig consumerConfig);


    /**
     * ������Ϣ�����ߣ�ʹ��ָ����offset�洢��
     * 
     * @param consumerConfig
     *            ����������
     * @param offsetStorage
     *            offset�洢��
     * @return
     */
    public MessageConsumer createConsumer(ConsumerConfig consumerConfig, OffsetStorage offsetStorage);

}