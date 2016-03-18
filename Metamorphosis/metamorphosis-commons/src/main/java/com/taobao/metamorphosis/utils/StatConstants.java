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

/**
 * ͳ�����Ƴ���
 * 
 * @author boyan
 * @Date 2011-5-8
 * 
 */
public class StatConstants {
    /**
     * ������Ϣʱ��ͳ��
     */
    public static final String PUT_TIME_STAT = "cli_put_time";
    /**
     * ������Ϣ���һ��ֵ��ͳ��
     */
    public static final String PUT_TIMEOUT_STAT = "cli_put_timeout";

    /**
     * ������Ϣ�����������Ϣ��
     */
    public static final String SKIP_MSG_COUNT = "cli_skip_msg_count";

    /**
     * ������Ϣ���Դ���ͳ��
     */
    public static final String PUT_RETRY_STAT = "cli_put_retry";

    /**
     * ��ȡ��Ϣʱ��ͳ��
     */
    public static final String GET_TIME_STAT = "cli_get_timeout";

    /**
     * ��ȡ��Ϣʧ��ͳ��
     */
    public static final String GET_FAILED_STAT = "cli_get_failed";

    /**
     * Offset��ѯͳ��
     */
    public static final String OFFSET_TIME_STAT = "cli_offset_timeout";
    /**
     * Offset��ѯʧ��ͳ��
     */
    public static final String OFFSET_FAILED_STAT = "cli_offset_failed";

    /**
     * ��ȡ��Ϣ����ͳ��
     */
    public static final String GET_MSG_COUNT_STAT = "cli_get_msg_count";

    /**
     * �Ƿ���Ϣͳ��
     */
    public static final String INVALID_MSG_STAT = "cli_invalid_message";

    /**
     * �����put��Ϣʧ��
     */
    public static final String PUT_FAILED = "put_failed";

    /**
     * �����put��Ϣʧ��
     */
    public static final String GET_FAILED = "get_failed";

    /**
     * ����˽��յ�����Ϣ��С
     */
    public static final String MESSAGE_SIZE = "message_size";

    /**
     * �����get miss
     */
    public static final String GET_MISS = "get_miss";

    /**
     * ����˴���get����
     */
    public static final String CMD_GET = "get";

    /**
     * ����˴���offset
     */
    public static final String CMD_OFFSET = "offset";

    /**
     * ����˴���put
     */
    public static final String CMD_PUT = "put";

    /**
     * ��ʼ������
     */
    public static final String TX_BEGIN = "txBegin";

    /**
     * end������
     */
    public static final String TX_END = "txEnd";

    /**
     * prepare������
     */
    public static final String TX_PREPARE = "txPrepare";

    /**
     * �ύ������
     */
    public static final String TX_COMMIT = "txCommit";

    /**
     * �ع�������
     */
    public static final String TX_ROLLBACK = "txRollback";

    /**
     * ����ƽ��ִ��ʱ��
     */
    public static final String TX_TIME = "txExecTime";

}