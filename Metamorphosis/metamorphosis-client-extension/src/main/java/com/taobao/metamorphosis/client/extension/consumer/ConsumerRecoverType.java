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
package com.taobao.metamorphosis.client.extension.consumer;

import com.taobao.metamorphosis.client.MetaMessageSessionFactory;
import com.taobao.metamorphosis.client.consumer.RecoverManager;


/**
 * ��������Ϣ����ʧ��ʱ��recover��ʽ
 * 
 * @author �޻�
 * @since 2011-11-7 ����5:20:06
 */

public enum ConsumerRecoverType {

    /**
     * Ĭ������,��Ҫrecover����Ϣ�洢�ڱ���
     * 
     */
    DEFAULT {
        @Override
        public RecoverManager getRecoverManager(final MetaMessageSessionFactory factory) {
            return factory.getRecoverStorageManager();
        }
    },

    /**
     * ��Ҫrecover����Ϣ�洢��Notify.<br>
     * <b>unsupported,"notify" is not open source yet<b>
     * 
     */
    NOTIFY {
        @Override
        public RecoverManager getRecoverManager(final MetaMessageSessionFactory factory) {
            return this.getNotifyRecoverManager(factory, null);
        }

    };

    public RecoverManager getRecoverManager(final MetaMessageSessionFactory factory) {
        throw new AbstractMethodError();
    }


    public RecoverManager getNotifyRecoverManager(final MetaMessageSessionFactory factory,
            final String notifyRecoverTopic) {
        return new RecoverNotifyManager(factory, notifyRecoverTopic, factory.getRecoverStorageManager());
    }
}