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

import java.io.IOException;

import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.Shutdownable;
import com.taobao.metamorphosis.cluster.Partition;


/**
 * ��Ϣ�ݴ��recover�������ĳ���
 * 
 * @author �޻�
 * @since 2011-10-27 ����3:34:12
 */

public interface MessageRecoverManager extends Shutdownable {

    /**
     * ȫ���ָ�
     */
    public void recover();


    /**
     * �����ָ�һ������һ����������Ϣ
     * 
     * @param topic
     * @param partition
     * @param recoverer
     *            �ָ���������Ϣ�Ĵ�����
     * @return �Ƿ������ύ�˻ָ�����
     * */
    public boolean recover(final String topic, final Partition partition, final MessageRecoverer recoverer);


    /**
     * ������Ϣ
     * 
     * @param message
     * @param partition
     * @throws IOException
     */
    public void append(Message message, Partition partition) throws IOException;


    /**
     * ��Ϣ����
     * 
     * @param topic
     * @param partition
     * @return
     */
    public int getMessageCount(String topic, Partition partition);


    /**
     * ������λָ���Ϣ�Ĵ�����
     * 
     * @param recoverer
     */
    public void setMessageRecoverer(MessageRecoverer recoverer);

    /**
     * ָ����Ϣ���recover
     * 
     * @author wuhua
     * 
     */
    public static interface MessageRecoverer {
        /**
         * recover��������Ϣ��δ���
         * 
         * @param msg
         * @throws Exception
         */
        public void handle(Message msg) throws Exception;
    }
}