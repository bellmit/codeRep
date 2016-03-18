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
package com.taobao.metamorphosis.client.consumer.storage;

import java.util.Collection;

import com.taobao.metamorphosis.client.consumer.TopicPartitionRegInfo;
import com.taobao.metamorphosis.cluster.Partition;


/**
 * Offset�洢���ӿ�
 * 
 * @author boyan
 * @Date 2011-4-28
 * 
 */
public interface OffsetStorage {
    /**
     * ����offset���洢
     * 
     * @param group
     *            ����������
     * @param infoList
     *            �����߶��ĵ���Ϣ������Ϣ�б�
     */
    public void commitOffset(String group, Collection<TopicPartitionRegInfo> infoList);


    /**
     * ����һ�������ߵĶ�����Ϣ����������ڷ���null
     * 
     * @param topic
     * @param group
     * @param partiton
     * @return
     */
    public TopicPartitionRegInfo load(String topic, String group, Partition partition);


    /**
     * �ͷ���Դ��meta�ͻ����ڹرյ�ʱ����������ô˷���
     */
    public void close();


    /**
     * ��ʼ��offset
     * 
     * @param topic
     * @param group
     * @param partition
     * @param offset
     */
    public void initOffset(String topic, String group, Partition partition, long offset);
}