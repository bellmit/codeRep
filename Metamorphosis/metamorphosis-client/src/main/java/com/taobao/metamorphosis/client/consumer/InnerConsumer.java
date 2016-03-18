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
package com.taobao.metamorphosis.client.consumer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.exception.MetaClientException;


/**
 * �������ṩ��consumer�ӿڣ������ṩ��Fetchʹ��
 * 
 * @author boyan(boyan@taobao.com)
 * @date 2011-9-13
 * 
 */
public interface InnerConsumer {

    /**
     * ץȡ��Ϣ
     * 
     * @param fetchRequest
     * @param timeout
     * @param timeUnit
     * @return
     * @throws MetaClientException
     * @throws InterruptedException
     */
    MessageIterator fetch(final FetchRequest fetchRequest, long timeout, TimeUnit timeUnit) throws MetaClientException,
            InterruptedException;


    /**
     * ����topic��Ӧ����Ϣ������
     * 
     * @param topic
     * @return
     */
    MessageListener getMessageListener(final String topic);


    /**
     * �����޷����ͻ������ѵ���Ϣ
     * 
     * @param message
     * @throws IOException
     */
    void appendCouldNotProcessMessage(final Message message) throws IOException;


    /**
     * ��ѯoffset
     * 
     * @param fetchRequest
     * @return
     * @throws MetaClientException
     */
    long offset(final FetchRequest fetchRequest) throws MetaClientException;

}