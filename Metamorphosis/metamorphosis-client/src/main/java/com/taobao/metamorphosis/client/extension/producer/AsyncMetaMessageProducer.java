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

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.gecko.core.command.ResponseCommand;
import com.taobao.gecko.core.command.ResponseStatus;
import com.taobao.gecko.core.command.kernel.BooleanAckCommand;
import com.taobao.gecko.service.Connection;
import com.taobao.gecko.service.SingleRequestCallBackListener;
import com.taobao.gecko.service.exception.NotifyRemotingException;
import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.MetaMessageSessionFactory;
import com.taobao.metamorphosis.client.RemotingClientWrapper;
import com.taobao.metamorphosis.client.extension.producer.MessageRecoverManager.MessageRecoverer;
import com.taobao.metamorphosis.client.producer.PartitionSelector;
import com.taobao.metamorphosis.client.producer.ProducerZooKeeper;
import com.taobao.metamorphosis.client.producer.SimpleMessageProducer;
import com.taobao.metamorphosis.cluster.Partition;
import com.taobao.metamorphosis.exception.InvalidMessageException;
import com.taobao.metamorphosis.exception.MetaClientException;
import com.taobao.metamorphosis.network.BooleanCommand;
import com.taobao.metamorphosis.network.HttpStatus;
import com.taobao.metamorphosis.network.PutCommand;


/**
 * <pre>
 * �첽��������Ϣ����������������ʵ��.
 * 
 * ʹ�ó���:
 *      ���ڷ��Ϳɿ���Ҫ����ô��,��Ҫ����߷���Ч�ʺͽ��Ͷ�����Ӧ�õ�Ӱ�죬�������Ӧ�õ��ȶ���.
 *      ����,�ռ���־���û���Ϊ��Ϣ�ȳ���.
 * ע��:
 *      ������Ϣ�󷵻صĽ���в�����׼ȷ��messageId��offset,��Щֵ����-1
 * 
 * @author �޻�
 * @since 2011-10-21 ����1:42:55
 * </pre>
 */

public class AsyncMetaMessageProducer extends SimpleMessageProducer implements AsyncMessageProducer, MessageRecoverer {
    private static final Log log = LogFactory.getLog(AsyncMetaMessageProducer.class);
    private static final int DEFAULT_PERMITS = 20000;

    private final SlidingWindow slidingWindow;

    private IgnoreMessageProcessor ignoreMessageProcessor;


    public AsyncMetaMessageProducer(final MetaMessageSessionFactory messageSessionFactory,
            final RemotingClientWrapper remotingClient, final PartitionSelector partitionSelector,
            final ProducerZooKeeper producerZooKeeper, final String sessionId, final int slidingWindowSize0,
            final IgnoreMessageProcessor processor) {

        super(messageSessionFactory, remotingClient, partitionSelector, producerZooKeeper, sessionId);
        this.slidingWindow = new SlidingWindow(slidingWindowSize0 > 0 ? slidingWindowSize0 : DEFAULT_PERMITS);
        this.ignoreMessageProcessor =
                processor != null ? processor : new AsyncIgnoreMessageProcessor(
                    messageSessionFactory.getMetaClientConfig(), this);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.taobao.metamorphosis.client.extension.producer.AsyncMessageProducer
     * #asyncSendMessage(com.taobao.metamorphosis.Message)
     */
    @Override
    public void asyncSendMessage(final Message message) {
        this.asyncSendMessage(message, DEFAULT_OP_TIMEOUT, TimeUnit.MILLISECONDS);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.taobao.metamorphosis.client.extension.producer.AsyncMessageProducer
     * #asyncSendMessage(com.taobao.metamorphosis.Message, long,
     * java.util.concurrent.TimeUnit)
     */
    @Override
    public void asyncSendMessage(final Message message, final long timeout, final TimeUnit unit) {
        try {
            super.sendMessage(message, timeout, unit);
        }
        catch (final IllegalStateException e) {
            // ������producer�ѹر�
            log.warn(e);
        }
        catch (final InvalidMessageException e) {
            // �Ƿ�����Ϣ,������Ϣֱ���ӵ�,���ڱ��ػ���Զ��recover����ȥ
            log.warn(e);
        }
        catch (final MetaClientException e) {
            // ������ʧ�ܵ���Ϣ
            if (log.isDebugEnabled()) {
                log.debug("save to local strage,and waitting for recover. cause:" + e.getMessage());
            }
            this.handleSendFailMessage(message);
        }
        catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        catch (final Throwable e) {
            // ����û��Ԥ�ϵ������
            if (log.isDebugEnabled()) {
                log.debug("save to local strage,and waitting for recover. cause:", e);
            }
            this.handleSendFailMessage(message);
        }
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.taobao.metamorphosis.client.extension.producer.AsyncMessageProducer
     * #setIgnoreMessageProcessor
     * (com.taobao.metamorphosis.client.extension.producer
     * .AsyncMessageProducer.IgnoreMessageProcessor)
     */
    @Override
    public void setIgnoreMessageProcessor(final IgnoreMessageProcessor ignoreMessageProcessor) {
        this.ignoreMessageProcessor = ignoreMessageProcessor;
    }


    @Override
    protected BooleanCommand invokeToGroup(final String serverUrl, final Partition partition,
            final PutCommand putCommand, final Message message, final long timeout, final TimeUnit unit)
            throws InterruptedException, TimeoutException, NotifyRemotingException {

        try {
            return this.trySend(serverUrl, putCommand, timeout, unit);
        }
        catch (final MetaMessageOverflowException e) {
            if (log.isDebugEnabled()) {
                log.debug("save to local strage,and waitting for recover. cause:" + e.getMessage());
            }
            return this.processOverMessage(partition, putCommand, message, e);
        }

    }


    // ���ϲ�����������,����������ݰ����remoting�����OOM
    private BooleanCommand trySend(final String serverUrl, final PutCommand putCommand, final long timeout,
            final TimeUnit unit) throws NotifyRemotingException, InterruptedException {
        final int dataLength = putCommand.getData() != null ? putCommand.getData().length : 0;
        if (this.slidingWindow.tryAcquireByLength(dataLength)) {// , timeout,
                                                                // unit
            try {
                super.remotingClient.sendToGroup(serverUrl, putCommand, new MessageSendCallBackListener(putCommand),
                    timeout, unit);
                return new BooleanCommand(putCommand.getOpaque(), HttpStatus.Success, "-1 " + putCommand.getPartition()
                        + " -1");
            }
            catch (final NotifyRemotingException e) {
                this.slidingWindow.releaseByLenth(dataLength);
                if (e.getMessage().contains("������������") || e.getMessage().contains("������������CallBack����")) {
                    throw new MetaMessageOverflowException(e);
                }
                else {
                    throw e;
                }
            }
        }
        else {
            throw new MetaMessageOverflowException("������Ϣ���������������ڵ�λ������" + this.slidingWindow.getWindowsSize());
        }
    }


    private BooleanCommand processOverMessage(final Partition partition, final PutCommand putCommand,
            final Message message, final MetaMessageOverflowException e2) throws MetaMessageOverflowException {
        if (this.ignoreMessageProcessor != null) {
            // ���ܴ��������������سɹ�
            this.handleSendFailMessage(message);
            return new BooleanCommand(putCommand.getOpaque(), HttpStatus.Success, "-1 " + putCommand.getPartition()
                    + " -1");
        }
        else {
            throw e2;
        }
    }


    private void handleSendFailMessage(final Message message) {
        try {
            this.ignoreMessageProcessor.handle(message);
        }
        catch (final Exception e1) {
            log.warn(e1);
        }
    }

    /**
     * ��ʾ��Ϣ�������ص��쳣
     * 
     * @author wuhua
     * 
     */
    public static class MetaMessageOverflowException extends NotifyRemotingException {

        private static final long serialVersionUID = -1842231102008256662L;


        public MetaMessageOverflowException(final String string) {
            super(string);
        }


        public MetaMessageOverflowException(final Throwable e) {
            super(e);
        }

    }

    private class MessageSendCallBackListener implements SingleRequestCallBackListener {

        int dataLenth;
        AtomicBoolean released = new AtomicBoolean(false);


        MessageSendCallBackListener(final PutCommand putCommand) {
            final byte[] data = putCommand.getData();
            this.dataLenth = data != null ? data.length : 0;
        }


        @Override
        public void onResponse(final ResponseCommand responseCommand, final Connection conn) {
            this.release();

            if (responseCommand.getResponseStatus() != ResponseStatus.NO_ERROR) {
                final StringBuilder sb = new StringBuilder();
                sb.append("onResponse. Status:").append(responseCommand.getResponseStatus());
                if (responseCommand instanceof BooleanCommand) {
                    sb.append(",Code:").append(((BooleanCommand) responseCommand).getCode());
                }
                if (responseCommand instanceof BooleanAckCommand) {
                    sb.append(",ErrorMsg:").append(((BooleanAckCommand) responseCommand).getErrorMsg());
                    sb.append(",ResponseHost:").append(((BooleanAckCommand) responseCommand).getResponseHost());
                }
                log.warn(sb.toString());
            }
        }


        @Override
        public void onException(final Exception e) {
            this.release();
            log.warn(e);
        }


        private void release() {
            if (this.released.compareAndSet(false, true)) {
                AsyncMetaMessageProducer.this.slidingWindow.releaseByLenth(this.dataLenth);
            }
        }


        @Override
        public ThreadPoolExecutor getExecutor() {
            return null;
        }

    }


    @Override
    public void handle(final Message msg) throws Exception {
        this.asyncSendMessage(msg);
    }


    // for test
    IgnoreMessageProcessor getIgnoreMessageProcessor() {
        return ignoreMessageProcessor;
    }

}