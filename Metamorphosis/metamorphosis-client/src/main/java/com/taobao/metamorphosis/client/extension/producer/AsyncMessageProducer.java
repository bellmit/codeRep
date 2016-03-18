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

import java.util.concurrent.TimeUnit;

import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.producer.MessageProducer;


/**
 * <pre>
 * �첽������Ϣ��������.
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

public interface AsyncMessageProducer extends MessageProducer {

    /**
     * <pre>
     * �첽������Ϣ.
     * ����޶ȵļ��ٶ�ҵ�������̵�Ӱ��,ʹ���߲����ķ��ͳɹ���ʧ�ܺ������쳣
     * 
     * @param message
     * 
     * </pre>
     */
    public void asyncSendMessage(Message message);


    /**
     * <pre>
     * �첽������Ϣ. 
     * ����޶ȵļ��ٶ�ҵ�������̵�Ӱ��,ʹ���߲����ķ��ͳɹ���ʧ�ܺ������쳣
     * 
     * @param message
     * @param timeout
     * @param unit
     * </pre>
     */
    public void asyncSendMessage(Message message, long timeout, TimeUnit unit);


    /**
     * ���÷���ʧ�ܺͳ���������Ϣ�Ĵ�����,�û������Լ��ӹ���Щ��Ϣ��δ���
     * 
     * @param ignoreMessageProcessor
     */
    public void setIgnoreMessageProcessor(IgnoreMessageProcessor ignoreMessageProcessor);

    /**
     * ���ڴ�����ʧ�ܺͳ������ص���Ϣ
     * 
     * @author wuhua
     * 
     */
    public interface IgnoreMessageProcessor {
        /**
         * ����һ����Ϣ
         * 
         * @param message
         * @return
         * @throws Exception
         */
        boolean handle(Message message) throws Exception;
    }
}