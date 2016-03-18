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
package com.taobao.metamorphosis.client.extension.producer;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import com.taobao.common.store.Store;
import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.MetaClientConfig;
import com.taobao.metamorphosis.client.extension.storage.MessageStore;
import com.taobao.metamorphosis.cluster.Partition;


/**
 * ���˳����Ϣ�������⴦���LocalMessageStorageManager
 * 
 * @author �޻�
 * @since 2011-10-27 ����3:21:32
 */

public class OrderedLocalMessageStorageManager extends LocalMessageStorageManager {

    public OrderedLocalMessageStorageManager(MetaClientConfig metaClientConfig) {
        super(metaClientConfig);
    }


    @Override
    public void recover() {
        Set<String> names = this.topicStoreMap.keySet();
        if (names == null || names.size() == 0) {
            log.info("SendRecoverû����Ҫ�ָ�����Ϣ");
            return;
        }

        if (this.messageRecoverer != null) {
            for (String name : names) {
                String[] tmps = name.split(SPLIT);
                String topic = tmps[0];
                Partition partition = new Partition(tmps[1]);
                if (!partition.equals(Partition.RandomPartiton) && this.getMessageCount(topic, partition) > 0) {
                    // RandomPartiton��������recover
                    this.recover(topic, partition, this.messageRecoverer);
                }
            }
        }
        else {
            log.warn("messageRecoverer��δ����");
        }
    }


    /**
     * �����ָ�һ������һ����������Ϣ,�ɶ�ε���(��֤��ĳ����Ļָ��������ֻ��һ��������)
     * 
     * @param topic
     * @param partition
     * @param recoverer
     *            �ָ���������Ϣ�Ĵ�����
     * @return �Ƿ������ύ�˻ָ�����
     * */
    @Override
    public boolean recover(final String topic, final Partition partition, final MessageRecoverer recoverer) {

        final String name = this.generateKey(topic, partition);
        FutureTask<Boolean> recoverTask = new FutureTask<Boolean>(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                try {
                    int count = 0;
                    Store randomPartitonStore =
                            OrderedLocalMessageStorageManager.this.getOrCreateStore(topic, Partition.RandomPartiton);

                    // ���ָܻ��ĸ����������Ȼָ�һ��δ֪����������(ԭ����,����������ݵĲ���ʱ���������ھ������)
                    // ������ͬһ��topic��δ֪�������лָ�������ᵼ�������ظ�����
                    if (randomPartitonStore.size() > 0) {
                        // ������ʱ�Ŷ�randomPartitonStoreͬ������
                        synchronized (randomPartitonStore) {
                            // ˫�ؼ��
                            if (randomPartitonStore.size() > 0) {
                                count = this.innerRecover(randomPartitonStore, recoverer);
                                log.info("SendRecover topic=" + topic + "@-1--1�ָ���Ϣ" + count + "��");
                            }
                        }
                    }

                    Store store = OrderedLocalMessageStorageManager.this.getOrCreateStore(topic, partition);

                    count = this.innerRecover(store, recoverer);
                    log.info("SendRecover topic=" + name + "�ָ���Ϣ" + count + "��");
                }
                catch (Throwable e) {
                    log.error("SendRecover������Ϣ�ָ�ʧ��,topic=" + name, e);
                }
                finally {
                    log.info("SendRecoverִ������Ƴ����ͻָ�����,topic=" + name);
                    OrderedLocalMessageStorageManager.this.topicRecoverTaskMap.remove(name);
                }
                return true;
            }


            private int innerRecover(Store store, final MessageRecoverer recoverer) throws IOException, Exception {
                Iterator<byte[]> it = store.iterator();
                int count = 0;
                while (it.hasNext()) {
                    byte[] key = it.next();
                    Message msg =
                            (Message) OrderedLocalMessageStorageManager.this.deserializer.decodeObject(store.get(key));
                    recoverer.handle(msg);
                    try {
                        store.remove(key);
                        count++;
                    }
                    catch (IOException e) {
                        log.error("SendRecover remove message failed", e);
                    }
                }
                return count;
            }
        });

        FutureTask<Boolean> ret = this.topicRecoverTaskMap.putIfAbsent(name, recoverTask);
        if (ret == null) {
            this.threadPoolExecutor.submit(recoverTask);
            return true;
        }
        else {
            if (log.isDebugEnabled()) {
                log.debug("SendRecover���ͻָ�������������,����Ҫ��������,topic=" + topic);
            }
            return false;
        }

    }


    @Override
    protected Store newStore(final String name) throws IOException {
        return new MessageStore(META_LOCALMESSAGE_PATH + File.separator + name, name);
    }

}