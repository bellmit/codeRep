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
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.common.store.Store;
import com.taobao.common.store.journal.JournalStore;
import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.MetaClientConfig;
import com.taobao.metamorphosis.cluster.Partition;
import com.taobao.metamorphosis.exception.GetRecoverStorageErrorException;
import com.taobao.metamorphosis.exception.UnknowCodecTypeException;
import com.taobao.metamorphosis.utils.IdWorker;
import com.taobao.metamorphosis.utils.NamedThreadFactory;
import com.taobao.metamorphosis.utils.codec.Deserializer;
import com.taobao.metamorphosis.utils.codec.Serializer;
import com.taobao.metamorphosis.utils.codec.impl.Hessian1Deserializer;
import com.taobao.metamorphosis.utils.codec.impl.Hessian1Serializer;
import com.taobao.metamorphosis.utils.codec.impl.JavaDeserializer;
import com.taobao.metamorphosis.utils.codec.impl.JavaSerializer;


/**
 * ��Ϣ�����ڱ��ش���,�����ڻ��ֶ�recover
 * 
 * @author �޻�
 * @since 2011-8-8 ����10:40:56
 */

public class LocalMessageStorageManager implements MessageRecoverManager {

    protected static final String SPLIT = "@";

    /**
     * ��ʾ��topic@partitionΪ��λ��store map
     */
    protected final ConcurrentHashMap<String/* topic@partition */, FutureTask<Store>> topicStoreMap =
            new ConcurrentHashMap<String, FutureTask<Store>>();

    /**
     * ��ʾ����ִ�еĻָ������map
     */
    protected final ConcurrentHashMap<String/* topic@partition */, FutureTask<Boolean>> topicRecoverTaskMap =
            new ConcurrentHashMap<String, FutureTask<Boolean>>();

    private final Serializer serializer;
    protected final Deserializer deserializer;
    static final Log log = LogFactory.getLog(LocalMessageStorageManager.class);
    private final IdWorker idWorker = new IdWorker(0);

    public static final String DEFAULT_META_LOCALMESSAGE_PATH = System.getProperty("meta.localmessage.path",
        System.getProperty("user.home") + File.separator + ".meta_localmessage");

    public String META_LOCALMESSAGE_PATH;

    private final String META_LOCALMESSAGE_CODEC_TYPE = System.getProperty("meta.localmessage.codec", "java");

    // �ָ���Ϣ���̳߳�
    protected final ThreadPoolExecutor threadPoolExecutor;

    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();


    public LocalMessageStorageManager(final MetaClientConfig metaClientConfig) {
        this(metaClientConfig, DEFAULT_META_LOCALMESSAGE_PATH, null);
    }


    public LocalMessageStorageManager(final MetaClientConfig metaClientConfig, final String path,
            final MessageRecoverer messageRecoverer) {
        super();
        this.META_LOCALMESSAGE_PATH = StringUtils.isNotBlank(path) ? path : DEFAULT_META_LOCALMESSAGE_PATH;
        this.messageRecoverer = messageRecoverer;

        if (this.META_LOCALMESSAGE_CODEC_TYPE.equals("java")) {
            this.serializer = new JavaSerializer();
            this.deserializer = new JavaDeserializer();
        }
        else if (this.META_LOCALMESSAGE_CODEC_TYPE.equals("hessian1")) {
            this.serializer = new Hessian1Serializer();
            this.deserializer = new Hessian1Deserializer();
        }
        else {
            throw new UnknowCodecTypeException(this.META_LOCALMESSAGE_CODEC_TYPE);
        }

        // ��������Ϣ��RecoverThreadCountһ��
        this.threadPoolExecutor =
                new ThreadPoolExecutor(metaClientConfig.getRecoverThreadCount(),
                    metaClientConfig.getRecoverThreadCount(), 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(
                        100), new NamedThreadFactory("SendRecover-thread"), new ThreadPoolExecutor.CallerRunsPolicy());

        this.makeDataDir();
        this.loadStores();

        // ��ʱȥ���Իָ�һ������,����ĳ��topic��ʱ��û����Ϣʱ���ػ������Ϣһֱ����ѹ�ڱ���
        this.scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                log.info("��ʼ���Է��ͱ��ػ������Ϣ...");
                LocalMessageStorageManager.this.recover();
            }
        }, 0, metaClientConfig.getRecoverMessageIntervalInMills(), TimeUnit.MILLISECONDS);

    }


    private void loadStores() {
        final File dataPath = new File(this.META_LOCALMESSAGE_PATH);
        final File[] files = dataPath.listFiles();
        for (final File subFile : files) {
            if (subFile.isDirectory()) {
                final String name = subFile.getName();
                final String[] tmps = name.split(SPLIT);
                if (tmps.length != 2) {
                    continue;
                }

                log.info("����local message storage " + name + " ...");
                this.getOrCreateStore(tmps[0], new Partition(tmps[1]));
            }
        }
    }


    @Override
    public void recover() {
        final Set<String> names = this.topicStoreMap.keySet();
        if (names == null || names.size() == 0) {
            log.info("SendRecoverû����Ҫ�ָ�����Ϣ");
            return;
        }

        if (this.messageRecoverer != null) {
            for (final String name : names) {
                final String[] tmps = name.split(SPLIT);
                final String topic = tmps[0];
                final Partition partition = new Partition(tmps[1]);
                final int count = this.getMessageCount(topic, partition);
                log.info(name + "��Ҫ�ָ�������:" + count);
                if (count > 0) {
                    if (!this.recover(topic, partition, this.messageRecoverer)) {
                        log.info("SendRecover���ͻָ�������������,����Ҫ��������,name=" + name);
                    }
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
        final FutureTask<Boolean> recoverTask = new FutureTask<Boolean>(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                final AtomicLong count = new AtomicLong(0);
                try {

                    final Store store = LocalMessageStorageManager.this.getOrCreateStore(topic, partition);

                    this.innerRecover(store, recoverer, count, name);
                }
                catch (final Throwable e) {
                    log.error("SendRecover������Ϣ�ָ�ʧ��,name=" + name, e);
                }
                finally {
                    log.info("SendRecoverִ������Ƴ����ͻָ�����,name=" + name + ",�ָ���Ϣ" + count.get() + "��");
                    LocalMessageStorageManager.this.topicRecoverTaskMap.remove(name);
                }
                return true;
            }


            private void innerRecover(final Store store, final MessageRecoverer recoverer, final AtomicLong count,
                    final String name) throws IOException, Exception {
                final Iterator<byte[]> it = store.iterator();
                while (it.hasNext()) {
                    final byte[] key = it.next();
                    final Message msg =
                            (Message) LocalMessageStorageManager.this.deserializer.decodeObject(store.get(key));
                    recoverer.handle(msg);
                    try {
                        store.remove(key);
                        count.incrementAndGet();
                        if (count.get() % 20000 == 0) {
                            log.info("SendRecover " + name + "�ѻָ���Ϣ����:" + count.get());
                        }
                    }
                    catch (final IOException e) {
                        log.error("SendRecover remove message failed", e);
                    }
                }
            }
        });

        final FutureTask<Boolean> ret = this.topicRecoverTaskMap.putIfAbsent(name, recoverTask);
        if (ret == null) {
            this.threadPoolExecutor.submit(recoverTask);
            return true;
        }
        else {
            if (log.isDebugEnabled()) {
                log.debug("SendRecover���ͻָ�������������,����Ҫ��������,name=" + name);
            }
            return false;
        }

    }


    protected Store getOrCreateStore(final String topic, final Partition partition) {
        // Ϊtopic�ȴ���һ��RandomPartiton������store
        this.getOrCreateStore0(topic, Partition.RandomPartiton);
        return this.getOrCreateStore0(topic, partition);
    }


    private Store getOrCreateStore0(final String topic, final Partition partition) {
        final String name = this.generateKey(topic, partition);
        FutureTask<Store> task = this.topicStoreMap.get(name);
        if (task != null) {
            return this.getStore(name, task);
        }
        else {
            task = new FutureTask<Store>(new Callable<Store>() {

                @Override
                public Store call() throws Exception {
                    final File file =
                            new File(LocalMessageStorageManager.this.META_LOCALMESSAGE_PATH + File.separator + name);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    return this.newStore(name);
                }


                private Store newStore(final String name) throws IOException {
                    return LocalMessageStorageManager.this.newStore(name);
                }

            });
            FutureTask<Store> existsTask = this.topicStoreMap.putIfAbsent(name, task);
            if (existsTask == null) {
                task.run();
                existsTask = task;
            }
            return this.getStore(name, existsTask);
        }
    }


    private Store getStore(final String topic, final FutureTask<Store> task) {
        try {
            return task.get();
        }
        catch (final Throwable t) {
            log.error("��ȡtopic=" + topic + "��Ӧ��storeʧ��", t);
            throw new GetRecoverStorageErrorException("��ȡtopic=" + topic + "��Ӧ��storeʧ��", t);
        }

    }


    private void makeDataDir() {
        final File file = new File(this.META_LOCALMESSAGE_PATH);
        if (!file.exists()) {
            file.mkdir();
        }
    }


    @Override
    public void shutdown() {
        for (final Map.Entry<String, FutureTask<Store>> entry : this.topicStoreMap.entrySet()) {
            final String name = entry.getKey();
            final FutureTask<Store> task = entry.getValue();
            final Store store = this.getStore(name, task);
            try {
                store.close();
            }
            catch (final IOException e) {
                // ignore
            }
        }

    }


    @Override
    public void append(final Message message, final Partition partition) throws IOException {
        final Store store = this.getOrCreateStore(message.getTopic(), partition);
        final ByteBuffer buf = ByteBuffer.allocate(16);
        buf.putLong(this.idWorker.nextId());
        store.add(buf.array(), this.serializer.encodeObject(message));
    }


    @Override
    public int getMessageCount(final String topic, final Partition partition) {
        final String name = this.generateKey(topic, partition);
        final FutureTask<Store> task = this.topicStoreMap.get(name);
        if (task != null) {
            return this.getStore(name, task).size();
        }
        else {
            return 0;
        }
    }

    protected MessageRecoverer messageRecoverer;


    protected String generateKey(final String topic, final Partition partition) {
        return topic + SPLIT + partition;
    }


    @Override
    synchronized public void setMessageRecoverer(final MessageRecoverer recoverer) {
        if (this.messageRecoverer == null) {
            this.messageRecoverer = recoverer;
        }
    }


    protected Store newStore(final String name) throws IOException {
        return new JournalStore(this.META_LOCALMESSAGE_PATH + File.separator + name, name);
    }

}