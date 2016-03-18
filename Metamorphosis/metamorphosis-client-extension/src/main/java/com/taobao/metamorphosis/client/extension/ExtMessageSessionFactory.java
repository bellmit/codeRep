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

import com.taobao.metamorphosis.client.consumer.ConsumerConfig;
import com.taobao.metamorphosis.client.consumer.MessageConsumer;
import com.taobao.metamorphosis.client.consumer.storage.OffsetStorage;
import com.taobao.metamorphosis.client.extension.consumer.ConsumerRecoverType;


/**
 * һ����չ��Meta�Ự����,�ṩһЩ��չ����.
 * 
 * @author �޻�
 * @since 2011-11-7 ����4:06:15
 */

public interface ExtMessageSessionFactory extends BroadcastMessageSessionFactory {

    /**
     * ����������
     * 
     * @param consumerConfig
     *            ����������
     * @param RecoverType
     *            ѡ����Ϣrecover��ʽ,
     *            ConsumerRecoverType.DEFAULT,ConsumerRecoverType.NOTIFY<br>
     *            Ŀǰ��֧��ConsumerRecoverType.NOTIFY
     * @return
     */
    public MessageConsumer createConsumer(ConsumerConfig consumerConfig, ConsumerRecoverType RecoverType);


    /**
     * ����������
     * 
     * @param consumerConfig
     *            ����������
     * @param offsetStorage
     *            offset�洢��
     * @param RecoverType
     *            ѡ����Ϣrecover��ʽ,
     *            ConsumerRecoverType.DEFAULT,ConsumerRecoverType.NOTIFY<br>
     *            Ŀǰ��֧��ConsumerRecoverType.NOTIFY
     * @return
     */
    public MessageConsumer createConsumer(ConsumerConfig consumerConfig, OffsetStorage offsetStorage,
            ConsumerRecoverType recoverType);


    /**
     * �����㲥������
     * 
     * @param consumerConfig
     * @param recoverTypeѡ����Ϣrecover��ʽ
     *            , ConsumerRecoverType.DEFAULT,ConsumerRecoverType.NOTIFY.<br>
     *            Ŀǰ��֧��ConsumerRecoverType.NOTIFY
     * @return
     */
    public MessageConsumer createBroadcastConsumer(ConsumerConfig consumerConfig, ConsumerRecoverType recoverType);
}