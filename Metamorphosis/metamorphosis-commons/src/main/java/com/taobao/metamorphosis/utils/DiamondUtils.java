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
package com.taobao.metamorphosis.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.metamorphosis.cluster.Partition;
import com.taobao.metamorphosis.utils.ZkUtils.ZKConfig;


/**
 * ��diamond����������
 * 
 * @author boyan
 * @Date 2011-4-25
 * 
 */
public class DiamondUtils {
    public static final String DEFAULT_ZK_DATAID = "metamorphosis.zkConfig";
    public static final String DEFAULT_PARTITIONS_DATAID = "metamorphosis.partitions";
    static final Log log = LogFactory.getLog(DiamondUtils.class);


    /**
     * ��ȡzk���ã����û���׳�����ʱNPE
     * 
     * @param diamondManager
     * @param timeout
     * @return
     */
    // public static ZKConfig getZkConfig(final DiamondManager diamondManager,
    // final long timeout) {
    // final Properties props =
    // diamondManager.getAvailablePropertiesConfigureInfomation(timeout);
    // if (props != null) {
    // log.info("��diamond����zk���ã�" + props);
    // return getZkConfig(props);
    // }
    // return null;
    // }

    public static ZKConfig getZkConfig(final Properties props) {
        if (props != null) {
            boolean zkEnable = true;
            if (!com.taobao.gecko.core.util.StringUtils.isBlank(props.getProperty("zk.zkEnable"))) {
                zkEnable = Boolean.valueOf(props.getProperty("zk.zkEnable"));
            }
            String zkRoot = "/meta";
            if (!com.taobao.gecko.core.util.StringUtils.isBlank(props.getProperty("zk.zkRoot"))) {
                zkRoot = props.getProperty("zk.zkRoot");
            }
            final ZKConfig rt =
                    new ZKConfig(zkRoot, props.getProperty("zk.zkConnect"), Integer.parseInt(props
                        .getProperty("zk.zkSessionTimeoutMs")), Integer.parseInt(props
                        .getProperty("zk.zkConnectionTimeoutMs")), Integer.parseInt(props
                        .getProperty("zk.zkSyncTimeMs")), zkEnable);

            return rt;
        }
        else {
            throw new NullPointerException("Null zk config");
        }
    }


    // public static void getPartitions(final DiamondManager diamondManager,
    // final long timeout,
    // final Map<String, List<Partition>> partitionsMap) {
    // Properties props = null;
    // try {
    // props =
    // diamondManager.getAvailablePropertiesConfigureInfomation(timeout);
    // }
    // catch (final Exception e) {
    // log.warn(e.getMessage());
    // }
    //
    // log.info("��diamond����partitions���ã�" + props);
    // getPartitions(props, partitionsMap);
    // }

    /** ��ȡĳЩtopic������������Ϣ(ĳtopic������Щ����) */
    public static void getPartitions(final Properties properties, final Map<String, List<Partition>> ret) {
        log.info("��ʼ��������������Ϣ");
        final Map<String, List<Partition>> map = new HashMap<String, List<Partition>>();
        if (properties != null) {
            for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
                final String key = (String) entry.getKey();

                if (key != null && key.startsWith("topic.num.")) {
                    // keyȡtopic
                    final String topic = key.substring("topic.num.".length());
                    // value��ʽΪbrokerId0:num0;brokerId1:num1;...
                    final String value = (String) entry.getValue();
                    final List<Partition> partitions = parsePartitions(value);
                    if (partitions != null && !partitions.isEmpty()) {
                        map.put(topic, partitions);
                    }
                }
            }
            ret.clear();
            ret.putAll(map);
            if (!ret.isEmpty()) {
                log.info("����������Ϣ: " + map);
            }
            else {
                log.info("empty partitionsNum info");
            }
        }
        else {
            log.warn("Null partitionsNum config");
        }

    }


    private static String trim(final String str, final String stripChars, final int mode) {
        if (str == null) {
            return null;
        }

        final int length = str.length();
        int start = 0;
        int end = length;

        // ɨ���ַ���ͷ��
        if (mode <= 0) {
            if (stripChars == null) {
                while (start < end && Character.isWhitespace(str.charAt(start))) {
                    start++;
                }
            }
            else if (stripChars.length() == 0) {
                return str;
            }
            else {
                while (start < end && stripChars.indexOf(str.charAt(start)) != -1) {
                    start++;
                }
            }
        }

        // ɨ���ַ���β��
        if (mode >= 0) {
            if (stripChars == null) {
                while (start < end && Character.isWhitespace(str.charAt(end - 1))) {
                    end--;
                }
            }
            else if (stripChars.length() == 0) {
                return str;
            }
            else {
                while (start < end && stripChars.indexOf(str.charAt(end - 1)) != -1) {
                    end--;
                }
            }
        }

        if (start > 0 || end < length) {
            return str.substring(start, end);
        }

        return str;
    }


    /**
     * ����brokerId0:num0;brokerId1:num1;...��ʽ���ַ���Ϊpartition list
     * */
    static List<Partition> parsePartitions(final String value) {
        final String[] brokerNums = StringUtils.split(value, ";");

        if (brokerNums == null || brokerNums.length == 0) {
            return Collections.emptyList();
        }

        final List<Partition> ret = new LinkedList<Partition>();
        for (String brokerNum : brokerNums) {
            brokerNum = trim(brokerNum, ";", 0);
            final int index = brokerNum.indexOf(":");
            if (index < 0) {
                throw new NumberFormatException("ָ���ķ���������ʽ����,config string=" + value);
            }
            final int brokerId = Integer.parseInt(brokerNum.substring(0, index));
            final int num = Integer.parseInt(brokerNum.substring(index + 1));
            if (num <= 0) {
                throw new NumberFormatException("���������������0,config string=" + value);
            }
            for (int i = 0; i < num; i++) {
                ret.add(new Partition(brokerId, i));
            }
        }
        Collections.sort(ret);
        return ret;
    }
}