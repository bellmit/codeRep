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

import java.util.List;

import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.cluster.Partition;
import com.taobao.metamorphosis.exception.MetaClientException;


/**
 * <pre>
 * ֧�ֻ�ȡĳtopic��������,��ǰ���÷����������÷�������Ӧʱ ���׳�һ��������쳣
 * <code>AvailablePartitionNumException</code>, �Ա㷢����Ϣʱ��ʶ�����ʧ��ԭ��,�Ӷ�����Ӧ����.
 * 
 * ��Ҫ������Ϣ����(����ĳ��id)ɢ�е��̶�������Ҫ������ĳ�����ʹ��
 * </pre>
 * 
 * @author �޻�
 * @since 2011-8-2 ����4:41:35
 */
// ������NumAwarePartitionSelector��������Ϊ,�����÷����������õķ�����Ϣ��һ��ʱ,��һ����Ҫ������һ�ַ�ʽ����,
// ������չ���
public abstract class OrderedMessagePartitionSelector extends ConfigPartitionsSupport {

    @Override
    public Partition getPartition(String topic, List<Partition> partitions, Message message) throws MetaClientException {
        int availablePartitionNum = partitions != null ? partitions.size() : 0;
        List<Partition> configPartitions = this.getConfigPartitions(topic);
        int configPartitionsNum = configPartitions.size();

        // ˳����Ϣû�����ù�����������Ϣ,���÷���Ϣ
        if (configPartitionsNum == 0) {
            throw new MetaClientException("There is no config partitions for topic " + topic
                    + ",maybe you don't config it at first?");
        }

        Partition selectedPartition = this.choosePartition(topic, configPartitions, message);
        if (selectedPartition == null) {
            throw new MetaClientException("selected null partition");
        }

        // ���÷�����Ϊ0,����˳����Ϣ��Ϊ����ʱû�п��÷���(����ֻ��һ̨������,������������),����Ϊ��û����topic,
        // ������Ϣд���ػ���
        if (availablePartitionNum == 0) {
            throw new AvailablePartitionNumException("selected partition[" + selectedPartition + "]for topic[" + topic
                    + "]can not write now");
        }

        // ���÷��������÷�����������ѡ�����ķ���,(�������û������,�׳��쳣�û��Լ�����)
        if (!configPartitions.contains(selectedPartition) && !partitions.contains(selectedPartition)) {
            throw new MetaClientException("invalid selected partition:" + selectedPartition
                    + ",config and availabe paritions not contains it");
        }

        // ���÷��������÷���������ѡ�����ķ���ʱ�ſ�д
        if (configPartitions.contains(selectedPartition) && partitions.contains(selectedPartition)) {
            return selectedPartition;
        }
        else {
            // ѡ�������������������д.
            // 1.���÷�����Ϣ����
            // 2.���÷�������(������ͣ������)
            throw new AvailablePartitionNumException("selected partition[" + selectedPartition + "]for topic[" + topic
                    + "]can not write now");
        }

    }


    protected abstract Partition choosePartition(String topic, List<Partition> partitions, Message message);

}