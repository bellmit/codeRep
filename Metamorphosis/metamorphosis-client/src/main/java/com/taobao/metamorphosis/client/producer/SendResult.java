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
package com.taobao.metamorphosis.client.producer;

import com.taobao.metamorphosis.cluster.Partition;


/**
 * ��Ϣ���ͽ������
 * 
 * @author boyan
 * @Date 2011-4-27
 * 
 */
public class SendResult {
    private final boolean success;
    private final Partition partition;
    private final String errorMessage;
    private final long offset;


    public SendResult(boolean success, Partition partition, long offset, String errorMessage) {
        super();
        this.success = success;
        this.partition = partition;
        this.offset = offset;
        this.errorMessage = errorMessage;
    }


    /**
     * ����Ϣ���ͳɹ�����Ϣ�ڷ����д���offset���������ʧ�ܣ�����-1
     * 
     * @return
     */
    public long getOffset() {
        return this.offset;
    }


    /**
     * ��Ϣ�Ƿ��ͳɹ�
     * 
     * @return trueΪ�ɹ�
     */
    public boolean isSuccess() {
        return this.success;
    }


    /**
     * ��Ϣ����������ķ���
     * 
     * @return ��Ϣ����������ķ������������ʧ����Ϊnull
     */
    public Partition getPartition() {
        return this.partition;
    }


    /**
     * ��Ϣ���ͽ���ĸ�����Ϣ���������ʧ�ܿ��ܰ���������Ϣ
     * 
     * @return ��Ϣ���ͽ���ĸ�����Ϣ���������ʧ�ܿ��ܰ���������Ϣ
     */
    public String getErrorMessage() {
        return this.errorMessage;
    }

}