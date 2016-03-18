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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.transaction.xa.XAResource;

import com.taobao.gecko.core.util.ConcurrentHashSet;
import com.taobao.metamorphosis.client.MetaMessageSessionFactory;
import com.taobao.metamorphosis.client.RemotingClientWrapper;
import com.taobao.metamorphosis.client.transaction.TransactionContext;
import com.taobao.metamorphosis.exception.InvalidBrokerException;
import com.taobao.metamorphosis.exception.MetaClientException;


/**
 * XA��Ϣ�����ߵ�ʵ����
 * 
 * @author boyan
 * 
 */
public class SimpleXAMessageProducer extends SimpleMessageProducer implements XAMessageProducer {

    public SimpleXAMessageProducer(final MetaMessageSessionFactory messageSessionFactory,
            final RemotingClientWrapper remotingClient, final PartitionSelector partitionSelector,
            final ProducerZooKeeper producerZooKeeper, final String sessionId) {
        super(messageSessionFactory, remotingClient, partitionSelector, producerZooKeeper, sessionId);
    }

    final Set<String> publishedTopics = new ConcurrentHashSet<String>();

    // ������Ựѡ����broker url
    // TODO
    // ���������������
    // ��1�����TM��producer����ͬһ��Ӧ�ã���Ҫ��֤ÿ������ʹ�ù̶���url
    // ��2�����ʹ�õ��������TM��ÿ������ʹ�ò�ͬ��urlҲ���ԣ���ô����Ҫ���������ӶϿ���ʱ���ٴ�ѡ��һ��url
    private String selectedServerUrl;

    private final Random rand = new Random();


    @Override
    public void publish(final String topic) {
        super.publish(topic);
        if (this.publishedTopics.add(topic)) {
            this.selectedServerUrl = this.selectTransactionBroker();
        }
    }


    private String selectTransactionBroker() {
        final List<Set<String>> brokerUrls = new ArrayList<Set<String>>();
        for (final String topic : this.publishedTopics) {
            brokerUrls.add(this.producerZooKeeper.getServerUrlSetByTopic(topic));
        }
        final Set<String> resultSet = intersect(brokerUrls);
        if (resultSet.isEmpty()) {
            throw new InvalidBrokerException("Could not select a common broker url for  topics:" + this.publishedTopics);
        }
        final String[] urls = resultSet.toArray(new String[resultSet.size()]);
        Arrays.sort(urls);
        return urls[this.rand.nextInt(urls.length)];
    }


    static <T> Set<T> intersect(final List<Set<T>> sets) {
        if (sets == null || sets.size() == 0) {
            return null;
        }
        Set<T> rt = sets.get(0);
        for (int i = 1; i < sets.size(); i++) {
            final Set<T> copy = new HashSet<T>(rt);
            copy.retainAll(sets.get(i));
            rt = copy;
        }
        return rt;
    }


    @Override
    public XAResource getXAResource() throws MetaClientException {
        TransactionContext xares = this.transactionContext.get();
        if (xares != null) {
            return xares;
        }
        else {
            this.beginTransaction();
            xares = this.transactionContext.get();
            // ��������ѡ����broker
            xares.setServerUrl(this.selectedServerUrl);
            // ָ�����͵�url
            this.logLastSentInfo(this.selectedServerUrl);
            return xares;
        }
    }

}