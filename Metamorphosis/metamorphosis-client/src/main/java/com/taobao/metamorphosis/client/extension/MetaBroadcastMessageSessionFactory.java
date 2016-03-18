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

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.taobao.metamorphosis.client.MetaClientConfig;
import com.taobao.metamorphosis.client.MetaMessageSessionFactory;
import com.taobao.metamorphosis.client.consumer.ConsumerConfig;
import com.taobao.metamorphosis.client.consumer.MessageConsumer;
import com.taobao.metamorphosis.client.consumer.RecoverManager;
import com.taobao.metamorphosis.client.consumer.storage.LocalOffsetStorage;
import com.taobao.metamorphosis.exception.InvalidConsumerConfigException;
import com.taobao.metamorphosis.exception.MetaClientException;
import com.taobao.metamorphosis.network.RemotingUtils;


/**
 * �㲥��Ϣ�Ự����,ʹ�����������Consumer��ͬһ�����ڵ�ÿ̨���������յ�ͬһ����Ϣ,
 * �Ƽ�һ��Ӧ��ֻʹ��һ��MessageSessionFactory
 * 
 * @author �޻�
 * @since 2011-6-13 ����02:44:24
 */

public class MetaBroadcastMessageSessionFactory extends MetaMessageSessionFactory implements
        BroadcastMessageSessionFactory {

    public MetaBroadcastMessageSessionFactory(final MetaClientConfig metaClientConfig) throws MetaClientException {
        super(metaClientConfig);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.taobao.metamorphosis.client.extension.BroadcastMessageSessionFactory
     * #createBroadcastConsumer
     * (com.taobao.metamorphosis.client.consumer.ConsumerConfig)
     */
    @Override
    public MessageConsumer createBroadcastConsumer(final ConsumerConfig consumerConfig) {
        return this.createBroadcastConsumer(consumerConfig, null);
    }


    protected MessageConsumer createBroadcastConsumer(final ConsumerConfig consumerConfig,
            final RecoverManager recoverManager) {
        // �ȼ��һ��ԭʼ��group�Ƿ�Ϊ��
        if (StringUtils.isBlank(consumerConfig.getGroup())) {
            throw new InvalidConsumerConfigException("Blank group");
        }

        return this.createConsumer(updateGroupForBroadcast(consumerConfig), this.newLocalOffsetStorage(),
            recoverManager);
    }


    private LocalOffsetStorage newLocalOffsetStorage() {
        try {
            return new LocalOffsetStorage();
        }
        catch (final IOException e) {
            throw new InvalidConsumerConfigException("����Consumerʧ��,Create LocalOffsetStorage failed", e);
        }
    }


    static ConsumerConfig updateGroupForBroadcast(final ConsumerConfig consumerConfig) {
        try {
            consumerConfig.setGroup(consumerConfig.getGroup() + "-"
                    + RemotingUtils.getLocalAddress().replaceAll("[\\.\\:]", "-"));
            return consumerConfig;
        }
        catch (final Exception e) {
            throw new InvalidConsumerConfigException("��ȡ����ipʧ��", e);
        }
    }

}