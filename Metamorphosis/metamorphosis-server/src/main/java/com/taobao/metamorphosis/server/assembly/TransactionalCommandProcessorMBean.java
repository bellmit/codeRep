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
package com.taobao.metamorphosis.server.assembly;

/**
 * ��������MBean�ӿڣ��ṩһЩ��ѯ�͹����API
 * 
 * @author boyan(boyan@taobao.com)
 * @date 2011-8-29
 * 
 */
public interface TransactionalCommandProcessorMBean {

    /**
     * �������д���prepare״̬��xa����
     * 
     * @return
     */
    public String[] getPreparedTransactions() throws Exception;


    /**
     * �������д���prepare״̬��xa������Ŀ
     * 
     * @return
     */
    public int getPreparedTransactionCount() throws Exception;


    /**
     * �˹��ύ����
     * 
     * @param txKey
     */
    public void commitTransactionHeuristically(String txKey, boolean onePhase) throws Exception;


    /**
     * �˹��ع�����
     * 
     * @param txKey
     */
    public void rollbackTransactionHeuristically(String txKey) throws Exception;


    /**
     * �˹�������񣬲��ύҲ���ع�����ɾ��
     * 
     * @param txKey
     * @throws Exception
     */
    public void completeTransactionHeuristically(String txKey) throws Exception;

}