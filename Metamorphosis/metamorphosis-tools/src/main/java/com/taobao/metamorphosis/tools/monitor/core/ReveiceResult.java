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
package com.taobao.metamorphosis.tools.monitor.core;

import java.util.ArrayList;
import java.util.List;

import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.cluster.Partition;

/**
 *
 * @author �޻�
 * @since 2011-5-25 ����11:56:10
 */

/** ����һ�ν�����Ϣ�Ľ�� */
public class ReveiceResult {
    private String topic; //���յ�topic
    private Partition partition;//���ĸ�partition����
    private long offset;//��offset��ʼ����
    private String serverUrl;//���ĸ�����������
    private List<Message> messages;
    private Exception e;

    ReveiceResult(String topic, Partition partition, long offset, String serverUrl) {
        this.topic = topic;
        this.partition = partition;
        this.offset = offset;
        this.serverUrl = serverUrl;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        if (messages == null) {
            messages = new ArrayList<Message>();
        }
        messages.add(message);
    }

    public Exception getException() {
        return e;
    }

    public void setException(Exception e) {
        this.e = e;
    }

    /** �Ƿ���ճɹ�(���յ�����һ����Ϣ,����û�����쳣) **/
    public boolean isSuccess() {
        return messages != null && !messages.isEmpty() && e == null;
    }

    public String getTopic() {
        return topic;
    }

    public Partition getPartition() {
        return partition;
    }

    public long getOffset() {
        return offset;
    }

    public String getServerUrl() {
        return serverUrl;
    }

}