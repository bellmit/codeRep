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
package com.taobao.metamorphosis.client.extension.log4j;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.QuietWriter;
import org.apache.log4j.spi.LoggingEvent;

import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.MetaClientConfig;
import com.taobao.metamorphosis.client.extension.AsyncMessageSessionFactory;
import com.taobao.metamorphosis.client.extension.AsyncMetaMessageSessionFactory;
import com.taobao.metamorphosis.client.extension.producer.AsyncMessageProducer;
import com.taobao.metamorphosis.exception.MetaClientException;
import com.taobao.metamorphosis.utils.ZkUtils.ZKConfig;
import com.taobao.metamorphosis.utils.codec.CodecBuilder;
import com.taobao.metamorphosis.utils.codec.CodecBuilder.Codec_Type;


/**
 * 
 * @author wuxin,
 * @author boyan<boyan@taobao.com)
 * @since 1.0, 2009-10-20 ����03:29:12
 * @author wuhua
 */
public class StreamAppender extends AppenderSkeleton {

    /**
     * Immediate flush means that the underlying writer or output stream will be
     * flushed at the end of each append operation. Immediate flush is slower
     * but ensures that each append request is actually written. If
     * <code>immediateFlush</code> is set to <code>false</code>, then there is a
     * good chance that the last few logs events are not actually written to
     * persistent media if and when the application crashes.
     * 
     * <p>
     * The <code>immediateFlush</code> variable is set to <code>true</code> by
     * default.
     */
    protected boolean immediateFlush = true;

    /**
     * This is the {@link QuietWriter quietWriter} where we will write to.
     */
    protected OutputStream out;

    /**
     * Default topic is the same with name
     */
    protected String topic;

    /**
     * The <code>encodeType</code> variable is set to
     * <code>Codec_Type.HESSIAN</code> by default.
     */
    protected Codec_Type encodeType = Codec_Type.HESSIAN1;

    /**
     * Connection pool size for producer
     */
    protected int connectionPool = 1;

    protected AsyncMessageSessionFactory messageSessionFactory;

    protected AsyncMessageProducer producer;

    // /**
    // * ��diamond��ȡzk���õ�dataId��Ĭ��Ϊ"metamorphosis.zkConfig"
    // */
    // protected String diamondZKDataId = DiamondUtils.DEFAULT_ZK_DATAID;
    //
    // /**
    // * ��diamond��ȡzk���õ�group��Ĭ��ΪDEFAULT_GROUP
    // */
    // protected String diamondZKGroup = Constants.DEFAULT_GROUP;

    protected ZKConfig zkConfig = new ZKConfig();;


    private void initMeta() {
        if (this.producer == null) {
            synchronized (this) {
                if (this.producer == null) {
                    final MetaClientConfig metaClientConfig = new MetaClientConfig();
                    // metaClientConfig.setDiamondZKDataId(this.diamondZKDataId);
                    // metaClientConfig.setDiamondZKGroup(this.diamondZKGroup);
                    metaClientConfig.setZkConfig(this.zkConfig);
                    try {
                        if (this.messageSessionFactory == null) {
                            this.messageSessionFactory = new AsyncMetaMessageSessionFactory(metaClientConfig);
                        }
                        this.producer = this.messageSessionFactory.createAsyncProducer();
                    }
                    catch (final MetaClientException e) {
                        LogLog.error("Init meta producer failed" + this.out, e);
                    }
                }
            }
        }
    }


    /**
     * This default constructor does nothing.
     */
    public StreamAppender() {
        // this.initMeta();
    }


    /**
     * Instantiate a WriterAppender and set the output destination to
     * <code>writer</code>.
     * 
     * <p>
     * The <code>writer</code> must have been previously opened by the user.
     */
    public StreamAppender(final Layout layout, final OutputStream writer) {
        this.layout = layout;
        this.out = writer;
        // this.initMeta();
    }


    /**
     * If the <b>ImmediateFlush</b> option is set to <code>true</code>, the
     * appender will flush at the end of each write. This is the default
     * behavior. If the option is set to <code>false</code>, then the underlying
     * stream can defer writing to physical medium to a later time.
     * 
     * <p>
     * Avoiding the flush operation at the end of each append results in a
     * performance gain of 10 to 20 percent. However, there is safety tradeoff
     * involved in skipping flushing. Indeed, when flushing is skipped, then it
     * is likely that the last few log events will not be recorded on disk when
     * the application exits. This is a high price to pay even for a 20%
     * performance gain.
     */
    public void setImmediateFlush(final boolean value) {
        this.immediateFlush = value;
    }


    /**
     * Returns value of the <b>ImmediateFlush</b> option.
     */
    public boolean getImmediateFlush() {
        return this.immediateFlush;
    }


    public String getTopic() {
        return this.topic;
    }


    public void setTopic(final String topic) {
        this.topic = topic;
    }


    public void setZkConnect(final String zkConnect) {
        this.zkConfig.zkConnect = zkConnect;
        this.initMeta();
    }


    public Codec_Type getEncodeType() {
        return this.encodeType;
    }


    public void setEncodeType(final Codec_Type encodeType) {
        this.encodeType = encodeType;
    }


    /**
     * Set the name of this Appender.
     */
    @Override
    public void setName(final String name) {
        super.setName(name);
        // topic == name by default
        if (this.topic == null) {
            this.setTopic(this.topic);
        }
    }


    /**
     * Does nothing.
     */
    @Override
    public void activateOptions() {
    }


    /**
     * This method is called by the {@link AppenderSkeleton#doAppend} method.
     * 
     * <p>
     * If the output stream exists and is writable then write a log statement to
     * the output stream. Otherwise, write a single warning message to
     * <code>System.err</code>.
     * 
     * <p>
     * The format of the output will depend on this appender's layout.
     */
    @Override
    public void append(final LoggingEvent event) {

        // Reminder: the nesting of calls is:
        //
        // doAppend()
        // - check threshold
        // - filter
        // - append();
        // - checkEntryConditions();
        // - subAppend();

        if (!this.checkEntryConditions()) {
            return;
        }
        this.subAppend(event);
    }


    /**
     * This method determines if there is a sense in attempting to append.
     * 
     * <p>
     * It checks whether there is a set output target and also if there is a set
     * layout. If these checks fail, then the boolean value <code>false</code>
     * is returned.
     */
    protected boolean checkEntryConditions() {
        if (this.closed) {
            LogLog.warn("Not allowed to write to a closed appender.");
            return false;
        }

        if (this.out == null) {
            LogLog.error("No output stream or file set for the appender named [" + this.name + "].");
            return false;
        }

        if (this.layout == null) {
            LogLog.error("No layout set for the appender named [" + this.name + "].");
            return false;
        }
        return true;
    }


    /**
     * Actual writing occurs here.
     * 
     * <p>
     * Most subclasses of <code>WriterAppender</code> will need to override this
     * method.
     * 
     * @since 0.9.0
     */
    protected void subAppend(final LoggingEvent event) {
        try {
            Object message = null;
            message = event.getMessage();
            if (message == null) {
                return;
            }

            this.logObject(CodecBuilder.buildSerializer(this.encodeType).encodeObject(message));

            this.out.flush();
            /*
             * if (this.immediateFlush) { this.writer.flush(); }
             */
        }
        catch (final Exception e) {
            LogLog.error("Can not write data," + this.out, e);
        }
    }


    /**
     * ��Objectд��Log�ļ�ϵͳ���������ǽ��÷�����������֤����Objectд��ϵͳ��ԭ����.
     * 
     * Э���ʽ��
     * 
     * ��ʼ��ʾ(1���ֽ�) + ���󳤶� (4���ֽ�) + �汾��Ϣ (1���ֽڣ� + ���л����� (1���ֽ�) + �������� (���Ȳ��ޣ� +
     * ���󳤶� (4���ֽ�)
     */
    private synchronized void logObject(final byte[] content) throws IOException {
        if (this.producer != null) {
            this.producer.publish(this.topic);
            this.producer.asyncSendMessage(new Message(this.topic, content));
        }
        else {
            throw new IOException("Null producer");
        }
    }


    /**
     * Close this appender instance. The underlying stream or writer is also
     * closed.
     * 
     * <p>
     * Closed appenders cannot be reused.
     * 
     * @see #setWriter
     * @since 0.8.4
     */
    @Override
    public synchronized void close() {
        if (this.closed) {
            return;
        }
        this.closed = true;
        this.writeFooter();
        this.reset();
        if (this.producer != null) {
            try {
                this.producer.shutdown();
                this.messageSessionFactory.shutdown();
            }
            catch (final MetaClientException e) {
                // ignore
            }
        }
    }


    /**
     * Close the underlying {@link java.io.Writer}.
     * */
    protected void closeWriter() {
        if (this.out != null) {
            try {
                this.out.close();
            }
            catch (final IOException e) {
                // There is do need to invoke an error handler at this late
                // stage.
                LogLog.error("Could not close " + this.out, e);
            }
        }
    }


    /**
     * The WriterAppender requires a layout. Hence, this method returns
     * <code>true</code>.
     */
    @Override
    public boolean requiresLayout() {
        return true;
    }


    /**
     * Clear internal references to the writer and other variables.
     * 
     * Subclasses can override this method for an alternate closing behavior.
     */
    protected void reset() {
        this.closeWriter();
        this.out = null;
    }


    /**
     * Write a footer as produced by the embedded layout's
     * {@link Layout#getFooter} method.
     */
    protected void writeFooter() {
        if (this.out != null) {
            try {
                this.out.flush();
            }
            catch (final IOException e) {
                LogLog.error("Failed to flush writer,", e);
            }
        }
    }


    /**
     * Write a header as produced by the embedded layout's
     * {@link Layout#getHeader} method.
     */
    protected void writeHeader() {
        // Do nothing
    }
}