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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.extension.producer.AvailablePartitionNumException;
import com.taobao.metamorphosis.client.extension.producer.OrderedMessagePartitionSelector;
import com.taobao.metamorphosis.cluster.Partition;
import com.taobao.metamorphosis.exception.MetaClientException;


/**
 * 
 * @author �޻�
 * @since 2011-8-8 ����1:38:02
 */

public class OrderedMessagePartitionSelectorUnitTest {

    private OrderedMessagePartitionSelector selector;
    private final static String testTopic = "topic1";


    @Before
    public void setUp() {
        this.selector = new OrderedMessagePartitionSelector() {
            @Override
            protected Partition choosePartition(String topic, List<Partition> partitions, Message message) {
                return new Partition("0-0");
            }
        };
        Map<String, List<Partition>> map = new HashMap<String, List<Partition>>();
        map.put(testTopic, Arrays.asList(new Partition("0-0"), new Partition("0-1"), new Partition("1-0")));
        this.selector.setConfigPartitions(map);

    }


    @Test
    public void testGetPartition_normal() throws MetaClientException {
        // �������

        Message message = this.createDefaultMessage();
        Partition partition =
                this.selector.getPartition(message.getTopic(),
                    Arrays.asList(new Partition("0-0"), new Partition("0-1"), new Partition("1-0")), message);
        Assert.assertEquals(new Partition("0-0"), partition);
    }


    @Test(expected = MetaClientException.class)
    public void testGetPartition_configPartitionsNull() throws MetaClientException {
        // ����������Ϊnull
        this.selector.setConfigPartitions(null);
        Message message = this.createDefaultMessage();
        this.selector.getPartition(message.getTopic(),
            Arrays.asList(new Partition("0-0"), new Partition("0-1"), new Partition("1-0")), message);
    }


    @Test(expected = MetaClientException.class)
    public void testGetPartition_configPartitionsEmpty() throws MetaClientException {
        // ����������Ϊ��
        Map<String, List<Partition>> map = new HashMap<String, List<Partition>>();
        map.put(testTopic, new ArrayList<Partition>());
        this.selector.setConfigPartitions(map);
        Message message = this.createDefaultMessage();
        this.selector.getPartition(message.getTopic(),
            Arrays.asList(new Partition("0-0"), new Partition("0-1"), new Partition("1-0")), message);
    }


    @Test(expected = AvailablePartitionNumException.class)
    public void testGetPartition_availablePartitionsNull() throws MetaClientException {
        // ���÷���sΪnull
        Message message = this.createDefaultMessage();
        this.selector.getPartition(message.getTopic(), null, message);
    }


    @Test(expected = AvailablePartitionNumException.class)
    public void testGetPartition_availablePartitionsEmpty() throws MetaClientException {
        // ���÷���sΪ��
        Message message = this.createDefaultMessage();
        this.selector.getPartition(message.getTopic(), new ArrayList<Partition>(), message);
    }


    @Test
    public void testGetPartition_availablePartitionsChanged_butSelectedPartitionAvailable() throws MetaClientException {
        // ���÷��������˱仯������û��(0-1 -> 1-0),ѡ������ķ�����0-0����������������������������д��
        Message message = this.createDefaultMessage();
        Partition partition =
                this.selector.getPartition(message.getTopic(),
                    Arrays.asList(new Partition("0-0"), new Partition("1-0"), new Partition("2-0")), message);
        Assert.assertEquals(new Partition("0-0"), partition);
    }


    @Test(expected = AvailablePartitionNumException.class)
    public void testGetPartition_availablePartitionsChanged_andSelectedPartitionInvalid() throws MetaClientException {
        // ���÷�����������,����ѡ�����ķ�����0-0�����������ڿ��÷�����������û���д�˲����ڵ�һ��������
        Message message = this.createDefaultMessage();
        this.selector.getPartition(message.getTopic(),
            Arrays.asList(new Partition("1-0"), new Partition("2-0"), new Partition("3-0")), message);
    }


    @Test(expected = AvailablePartitionNumException.class)
    public void testGetPartition_configPartitionsChanged() throws MetaClientException {
        // �������÷����˱仯,���÷�������,ѡ������ķ�����0-0������д
        Map<String, List<Partition>> map = new HashMap<String, List<Partition>>();
        map.put(testTopic, Arrays.asList(new Partition("1-0"), new Partition("1-1"), new Partition("2-0")));
        this.selector.setConfigPartitions(map);

        Message message = this.createDefaultMessage();
        Partition partition =
                this.selector.getPartition(message.getTopic(),
                    Arrays.asList(new Partition("0-0"), new Partition("1-0"), new Partition("2-0")), message);
        Assert.assertEquals(new Partition("0-0"), partition);
    }


    @Test
    public void testGetPartition_availablePartitionsChanged_butSelectedPartitionAvailable2() throws MetaClientException {
        // ���÷���������(1-0),�������õķ���ѡ��,ѡ�����ķ���(0-0)�����ڿ��÷�����,��������������������������д��

        Message message = this.createDefaultMessage();
        Partition partition =
                this.selector.getPartition(message.getTopic(),
                    Arrays.asList(new Partition("0-0"), new Partition("0-1")), message);
        Assert.assertEquals(new Partition("0-0"), partition);
    }


    @Test(expected = AvailablePartitionNumException.class)
    public void testGetPartition_availablePartitionsChanged_butSelectedPartitionAvailable_expception()
            throws MetaClientException {
        // ���÷��������ˣ�0-0��,�������õķ���ѡ��,ѡ�����ķ�����0-0���������ڿ��÷�����

        Message message = this.createDefaultMessage();
        this.selector.getPartition(message.getTopic(), Arrays.asList(new Partition("0-1"), new Partition("1-0")),
            message);
    }


    private Message createDefaultMessage() {
        final String topic = testTopic;
        final byte[] data = "hello".getBytes();
        final Message message = new Message(topic, data);
        return message;
    }
}