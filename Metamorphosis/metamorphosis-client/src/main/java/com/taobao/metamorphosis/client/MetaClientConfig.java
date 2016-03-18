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
package com.taobao.metamorphosis.client;

import java.io.Serializable;
import java.util.Properties;

import com.taobao.metamorphosis.utils.DiamondUtils;
import com.taobao.metamorphosis.utils.ZkUtils.ZKConfig;


public class MetaClientConfig implements Serializable {
    static final long serialVersionUID = -1L;
    protected String serverUrl;

    /**
     * ��diamond��ȡzk���õ�dataId��Ĭ��Ϊ"metamorphosis.zkConfig"
     */
    protected String diamondZKDataId = DiamondUtils.DEFAULT_ZK_DATAID;

    /**
     * ��diamond��ȡzk���õ�group��Ĭ��ΪDEFAULT_GROUP
     */
    protected String diamondZKGroup = "DEFAULT_GROUP";// Constants.DEFAULT_GROUP;

    /**
     * ��diamond��ȡpartitions���õ�dataId��Ĭ��Ϊ"metamorphosis.partitions"
     * */
    private final String diamondPartitionsDataId = DiamondUtils.DEFAULT_PARTITIONS_DATAID;

    /**
     * ��diamond��ȡpartitions���õ�group��Ĭ��ΪDEFAULT_GROUP
     */
    private final String diamondPartitionsGroup = "DEFAULT_GROUP";// Constants.DEFAULT_GROUP;

    protected ZKConfig zkConfig;

    /**
     * recover������Ϣ��ʱ����
     */
    private long recoverMessageIntervalInMills = 5 * 60 * 1000L;

    private int recoverThreadCount = Runtime.getRuntime().availableProcessors();


    public int getRecoverThreadCount() {
        return this.recoverThreadCount;
    }


    public void setRecoverThreadCount(final int recoverThreadCount) {
        this.recoverThreadCount = recoverThreadCount;
    }


    public long getRecoverMessageIntervalInMills() {
        return this.recoverMessageIntervalInMills;
    }


    public void setRecoverMessageIntervalInMills(final long recoverMessageIntervalInMills) {
        this.recoverMessageIntervalInMills = recoverMessageIntervalInMills;
    }


    public String getDiamondZKDataId() {
        return this.diamondZKDataId;
    }


    public void setDiamondZKDataId(final String diamondZKDataId) {
        this.diamondZKDataId = diamondZKDataId;
    }


    public String getDiamondZKGroup() {
        return this.diamondZKGroup;
    }


    public void setDiamondZKGroup(final String diamondZKGroup) {
        this.diamondZKGroup = diamondZKGroup;
    }


    public ZKConfig getZkConfig() {
        return this.zkConfig;
    }


    public void setZkConfig(final ZKConfig zkConfig) {
        this.zkConfig = zkConfig;
    }


    public String getServerUrl() {
        return this.serverUrl;
    }


    public void setServerUrl(final String serverUrl) {
        this.serverUrl = serverUrl;
    }


    public String getDiamondPartitionsDataId() {
        return this.diamondPartitionsDataId;
    }


    public String getDiamondPartitionsGroup() {
        return this.diamondPartitionsGroup;
    }

    Properties partitionsInfo;


    /**
     * ����topic�ķֲ����.
     * ����ʹ���ϸ�˳������Ϣ��Ч(OrderedMessageProducer),Ŀǰ�汾û��diamond���Դ��������úͻ�ȡ�� <br>
     * partitionsInfo
     * .put("topic.num.exampleTopic1","brokerId1:��������;brokerId2:��������...")<br>
     * partitionsInfo
     * .put("topic.num.exampleTopic2","brokerId1:��������;brokerId2:��������...")
     * 
     * @param partitionsInfo
     */
    public void setPartitionsInfo(final Properties partitionsInfo) {
        this.partitionsInfo = partitionsInfo;
    }


    public Properties getPartitionsInfo() {
        return partitionsInfo;
    }

}