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
package com.taobao.metamorphosis;

import java.io.Serializable;
import java.util.Arrays;

import com.taobao.metamorphosis.cluster.Partition;


/**
 * һ��Metamorphosis����Ϣ����Ϣ������topic��data��Ҳ������һ�������ַ�������
 * 
 * @author boyan
 * @Date 2011-4-19
 * @author wuhua
 * @Date 2011-6-26
 * 
 */
public class Message implements Serializable {
    static final long serialVersionUID = -1L;
    private long id;
    private String topic;
    private byte[] data;
    private String attribute;
    private int flag;
    private Partition partition;


    void setId(final long id) {
        this.id = id;
    }


    int getFlag() {
        return this.flag;
    }


    void setFlag(final int flag) {
        this.flag = flag;
    }


    /**
     * ��Ϣ�Ƿ�������
     * 
     * @return
     */
    public boolean hasAttribute() {
        return this.attribute != null;
    }


    /**
     * �˷����Ѿ����������Ƿ���false�����ų�δ��ĳ���汾���Ƴ��˷������벻Ҫ��ʹ�á�
     * 
     * @return
     */
    @Deprecated
    public boolean isOrdered() {
        return false;
    }


    public Message(final String topic, final byte[] data) {
        super();
        this.topic = topic;
        this.data = data;
    }


    public Message(final String topic, final byte[] data, final String attribute) {
        super();
        this.topic = topic;
        this.data = data;
        this.attribute = attribute;
    }


    /**
     * ������ϢId��ֻ���ڷ��ͳɹ��󷵻ص�id����Ч�����򷵻�0
     * 
     * @return
     */
    public long getId() {
        return this.id;
    }


    /**
     * ������Ϣ����
     * 
     * @return
     */
    public String getAttribute() {
        return this.attribute;
    }


    /**
     * ������Ϣ���ԣ���Ϣ���ԷǱ��룬����Ϊ�ַ���
     * 
     * @param attribute
     */
    public void setAttribute(final String attribute) {
        this.attribute = attribute;
    }


    /**
     * ������Ϣtopic
     * 
     * @param topic
     */
    public void setTopic(final String topic) {
        this.topic = topic;
    }


    /**
     * ������Ϣpayload
     * 
     * @param data
     */
    public void setData(final byte[] data) {
        this.data = data;
    }


    /**
     * ������Ϣtopic
     * 
     * @return
     */
    public String getTopic() {
        return this.topic;
    }


    /**
     * ������Ϣpayload
     * 
     * @return
     */
    public byte[] getData() {
        return this.data;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.attribute == null ? 0 : this.attribute.hashCode());
        result = prime * result + Arrays.hashCode(this.data);
        result = prime * result + (int) (this.id ^ this.id >>> 32);
        result = prime * result + (this.topic == null ? 0 : this.topic.hashCode());
        return result;
    }


    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Message other = (Message) obj;
        if (this.attribute == null) {
            if (other.attribute != null) {
                return false;
            }
        }
        else if (!this.attribute.equals(other.attribute)) {
            return false;
        }
        if (!Arrays.equals(this.data, other.data)) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        if (this.topic == null) {
            if (other.topic != null) {
                return false;
            }
        }
        else if (!this.topic.equals(other.topic)) {
            return false;
        }
        return true;
    }


    void setPartition(final Partition partition) {
        this.partition = partition;
    }


    /**
     * ���ͳɹ��󣬷�����Ϣ���ڵķ���������ʧ����Ϊnull
     * 
     * @return
     */
    public Partition getPartition() {
        return this.partition;
    }

}