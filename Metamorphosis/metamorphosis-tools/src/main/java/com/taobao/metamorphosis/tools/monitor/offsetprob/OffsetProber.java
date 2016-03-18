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
package com.taobao.metamorphosis.tools.monitor.offsetprob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.taobao.metamorphosis.tools.domain.Group;
import com.taobao.metamorphosis.tools.domain.MetaServer;
import com.taobao.metamorphosis.tools.monitor.InitException;
import com.taobao.metamorphosis.tools.monitor.alert.Alarm;
import com.taobao.metamorphosis.tools.monitor.core.AbstractProber;
import com.taobao.metamorphosis.tools.monitor.core.CoreManager;
import com.taobao.metamorphosis.tools.monitor.core.MonitorConfig.GroupTopicPair;
import com.taobao.metamorphosis.tools.monitor.core.ProbTask;
import com.taobao.metamorphosis.tools.query.OffsetQueryDO;
import com.taobao.metamorphosis.tools.query.OffsetQueryDO.QueryType;
import com.taobao.metamorphosis.tools.query.Query;
import com.taobao.metamorphosis.tools.query.ZkOffsetStorageQuery;


/**
 * @author �޻�
 * @since 2011-5-31 ����11:06:05
 */

public class OffsetProber extends AbstractProber {

    private final static Logger logger = Logger.getLogger(OffsetProber.class);

    private Query query;

    private final Map<String, InnerOffsetValue> offsetMap = new HashMap<String, InnerOffsetValue>();

    private final Set<String> newKeySet = new HashSet<String>();


    public OffsetProber(CoreManager coreManager) {
        super(coreManager);
    }


    @SuppressWarnings("static-access")
    public void init() throws InitException {
        this.query = new Query();
        this.query.init(this.getMonitorConfig().getConfigPath(), null);
    }


    @Override
    protected void doProb() throws InterruptedException {
        this.futures.add(this.getProberExecutor().scheduleWithFixedDelay(new ProbTask() {

            @Override
            protected void doExecute() throws Exception {
                if (logger.isDebugEnabled()) {
                    logger.debug("offset prob...");
                }
                OffsetProber.this.probOnce();
            }


            @Override
            protected void handleException(Throwable e) {
                logger.error("unexpected error in offset prob thread.", e);
            }

        }, 0, this.getMonitorConfig().getOffsetProbCycleTime(), TimeUnit.HOURS));
//        }, 0, this.getMonitorConfig().getOffsetProbCycleTime()*1000*5, TimeUnit.MILLISECONDS));
        logger.debug("offset prob started");
    }

    private final List<ScheduledFuture<?>> futures = new ArrayList<ScheduledFuture<?>>();


    @Override
    protected void doStopProb() {
        cancelFutures(this.futures);
    }


    private void probOnce() {
        List<String> consumerGroups = this.query.getConsumerGroups(QueryType.zk);
        this.newKeySet.clear();
        for (String group : consumerGroups) {
            List<String> topicsList = this.query.getTopicsExistOffset(group, QueryType.zk);
            for (String topic : topicsList) {
            	List<String> partitions = this.query.getPartitionsOf(group, topic, QueryType.zk);
                for (String partition : partitions) {
                    long newOffset =ZkOffsetStorageQuery.parseOffsetAsLong(
                            this.query.queryOffset(new OffsetQueryDO(topic, group, partition, QueryType.zk.toString())));
                    String key = this.makeKey(group, topic, partition);
                    InnerOffsetValue newValue = new InnerOffsetValue(newOffset, System.currentTimeMillis());
                    InnerOffsetValue oldVlaue = this.offsetMap.get(key);
                    // �ɵļ�¼������,����offset�ƶ����Ĳ���Ҫput,��������ֵ�Ա�ȡ�����һ��offset�ƶ�����ʱ��
                    if (oldVlaue == null || newValue.offset != oldVlaue.offset) {
                        this.offsetMap.put(key, newValue);
                    }
                    this.newKeySet.add(key);
                    this.processOffset(newValue, oldVlaue, key);
                }
            }
        }
        this.processCancelConsumer(this.offsetMap, this.newKeySet);

    }

    private final static String altFormat = "consumer[%s] ���%sСʱ����û�н��չ���Ϣ,offsetͣ����%s,��һ��̽�⵽��ƫ������ʱ����%s";
    private final static String timeFormat = "yyyy-MM-dd HH:mm:ss";


    /** ���offset�������� �����Ҫ */
    private void processOffset(InnerOffsetValue newOffset, InnerOffsetValue oldOffset, String key) {
        logger.info(new StringBuilder("prosscess offset of [").append(key).append("],last offset[")
            .append(oldOffset != null ? oldOffset.offset : 0).append("],new offset[").append(newOffset.offset)
            .append("]").toString());

        if (newOffset == null || oldOffset == null) {
            return;
        }

        if (newOffset.offset == oldOffset.offset) {
        	float delta = ((float) (newOffset.timestamp - oldOffset.timestamp)) / (1000 * 3600);
            String msg =
                    String.format(altFormat, key, delta, oldOffset.offset,
                        new DateTime(oldOffset.timestamp).toString(timeFormat));
            logger.warn(msg);
            if (delta >= this.getMonitorConfig().getOffsetNoChangeTimeThreshold()) {
            	String topic = key.split(",")[1];
            	if(this.getMonitorConfig().getFilterTopicList().contains(topic)){
            		return;
            	}
                // ��������Ӧ�����ߵĸ�����,��������ʱ�ɸı�
                String[] tmp = StringUtils.split(key, ",");
                GroupTopicPair pair1 = new GroupTopicPair(tmp[0], tmp[1]);
                GroupTopicPair pair2 = new GroupTopicPair(tmp[0], null);
                List<String> wwList = this.findAlertList(this.getMonitorConfig().getGroupList(),"ww", pair1, pair2);
                List<String> mobileList =
                        this.findAlertList(this.getMonitorConfig().getGroupList(),"mobile", pair1, pair2);
                if(null==wwList){
                	wwList = new LinkedList<String>();
                }
                
                List<String> defaultWWList=this.getMonitorConfig().getWangwangList();
                for (String ww:defaultWWList) {
					if (!wwList.contains(ww)) {
						wwList.add(ww);
					}
				}
                logger.warn("alart to[" + wwList + "]mobiles[" + mobileList + "]");
                Alarm.start().wangwangs(wwList).mobiles(mobileList).alert(msg);
                
            }
        }
    }


//    private List<String> findAlertList(Map<GroupTopicPair, List<String>> map, GroupTopicPair... pairs) {
//        List<String> list = null;
//
//        if (map == null || map.isEmpty()) {
//            return null;
//        }
//        for (GroupTopicPair pair : pairs) {
//            list = map.get(pair);
//            if (list != null) {
//                break;
//            }
//        }
//        return list;
//    }
    
    private List<String> findAlertList(List<Group> groupList,String alertKind, GroupTopicPair... pairs) {
        List<String> list = null;
        if (groupList == null || groupList.isEmpty()) {
            return null;
        }
        for(Group group:groupList){
        	for (GroupTopicPair pair : pairs) {
        		if(group.getGroup().equals(pair.getGroup())&&group.getTopicList().contains(pair.getTopic())){
        			if("ww".equals(alertKind)){
        				return group.getWwList();
        			}else{
        				return group.getMobileList();
        			}
        		}
        	}
        }
        return list;
    }


    /** �����Ѿ������ڵľɼ�¼�������� */
    private void processCancelConsumer(Map<String, InnerOffsetValue> offsetMap, Set<String> newKeySet) {
        logger.info("start check all record");
        if (offsetMap.isEmpty()) {
            return;
        }
        Set<String> oldSet = new HashSet<String>(offsetMap.keySet());// note:����newһ���µ�
        for (String key : newKeySet) {
            oldSet.remove(key);
        }

        for (String key : oldSet) {
            offsetMap.remove(key);
            String msg = "�Ѿ����Ҳ���" + key + "�Ķ���ƫ������¼,������ȡ������,����";
            logger.warn(msg);
            String[] tmp = StringUtils.split(key, ",");
            GroupTopicPair pair = new GroupTopicPair(tmp[0], tmp[1]);
            List<String> wwList = this.findAlertList(this.getMonitorConfig().getGroupList(),"ww", pair);
            List<String> mobileList = this.findAlertList(this.getMonitorConfig().getGroupList(),"mobile", pair);
            Alarm.start().wangwangs(wwList).mobiles(mobileList);
        }
    }


    private String makeKey(String group, String topic, String partition) {
        int brokeId = Integer.parseInt(partition.substring(0, partition.indexOf("-")));
    	List<MetaServer> metaServerList = this.getMonitorConfig().getMetaServerList();
    	for(MetaServer metaServer:metaServerList){
    		if(metaServer.getBrokeId()==brokeId){
    			return group + "," + topic + "," + partition+","+metaServer.getHostIp()+","+metaServer.getHostName();
    		}
    	}
    	return group + "," + topic + "," + partition;
    }

    private static final class InnerOffsetValue {
        final long offset;
        final long timestamp;


        InnerOffsetValue(long offset, long timestamp) {
            this.offset = offset;
            this.timestamp = timestamp;
        }

    }

}