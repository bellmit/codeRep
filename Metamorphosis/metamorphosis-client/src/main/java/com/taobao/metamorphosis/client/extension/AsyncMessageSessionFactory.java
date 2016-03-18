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

import com.taobao.metamorphosis.client.MessageSessionFactory;
import com.taobao.metamorphosis.client.extension.producer.AsyncMessageProducer;
import com.taobao.metamorphosis.client.extension.producer.AsyncMessageProducer.IgnoreMessageProcessor;
import com.taobao.metamorphosis.client.producer.PartitionSelector;


/**
 * <pre>
 * ���ڴ����첽��������Ϣ�ĻỰ����. 
 * 
 * ʹ�ó���: 
 *      ���ڷ��Ϳɿ���Ҫ����ô��,��Ҫ����߷���Ч�ʺͽ��Ͷ�����Ӧ�õ�Ӱ�죬�������Ӧ�õ��ȶ���.
 *      ����,�ռ���־���û���Ϊ��Ϣ�ȳ���.
 * ע��:
 *      ������Ϣ�󷵻صĽ���в�����׼ȷ��messageId,partition,offset,��Щֵ����-1
 *      
 * @author �޻�
 * @since 2011-10-21 ����2:28:26
 * </pre>
 */

public interface AsyncMessageSessionFactory extends MessageSessionFactory {

    /**
     * �����첽�������Ϣ������
     * 
     * @return
     */
    public AsyncMessageProducer createAsyncProducer();


    /**
     * �����첽�������Ϣ������
     * 
     * @param partitionSelector
     *            ����ѡ����
     * @return
     */
    public AsyncMessageProducer createAsyncProducer(final PartitionSelector partitionSelector);


    /**
     * �����첽�������Ϣ������
     * 
     * @param partitionSelector
     *            ����ѡ����
     * @param slidingWindowSize
     *            ���Ʒ��������Ļ������ڴ�С,4k����ռ���ڵ�һ����λ,�ο�ֵ:���ڴ�СΪ20000�ȽϺ���. С��0����Ĭ��ֵ20000.
     *            ���ڿ���̫����ܵ���OOM����
     * @return
     */
    public AsyncMessageProducer createAsyncProducer(final PartitionSelector partitionSelector, int slidingWindowSize);


    /**
     * �����첽�������Ϣ������
     * 
     * @param partitionSelector
     *            ����ѡ����
     * @param slidingWindowSize
     *            ���Ʒ��������Ļ������ڴ�С,4k����ռ���ڵ�һ����λ,�ο�ֵ:���ڴ�СΪ20000�ȽϺ���. С��0����Ĭ��ֵ20000.
     *            ���ڿ���̫����ܵ���OOM����
     * @param processor
     *            ���÷���ʧ�ܺͳ���������Ϣ�Ĵ�����,�û������Լ��ӹ���Щ��Ϣ��δ���
     * 
     * 
     * @return
     */
    public AsyncMessageProducer createAsyncProducer(final PartitionSelector partitionSelector,
            IgnoreMessageProcessor processor);
}