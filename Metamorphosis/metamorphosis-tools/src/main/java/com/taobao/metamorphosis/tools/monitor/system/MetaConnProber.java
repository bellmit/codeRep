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
package com.taobao.metamorphosis.tools.monitor.system;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;

import com.taobao.metamorphosis.tools.monitor.core.CoreManager;
import com.taobao.metamorphosis.tools.monitor.core.MsgSender;
import com.taobao.metamorphosis.tools.utils.ConnectionUtil;
import com.taobao.metamorphosis.tools.utils.MonitorResult;
import com.taobao.metamorphosis.utils.Utils;
import com.taobao.metamorphosis.utils.Utils.Action;


/**
 * ������ӵ�Meta�������������������
 * 
 * @author �޻�
 * @since 2011-9-29 ����1:53:47
 */

public class MetaConnProber extends SystemProber {

    public MetaConnProber(CoreManager coreManager) {
        super(coreManager);
    }


    @Override
    protected MonitorResult getMonitorResult(final MsgSender sender) throws Exception {
        String result =
                ConnectionUtil.getConnectionInfo(sender.getHost(), sender.getPort(), this.getMonitorConfig()
                    .getLoginUser(), this.getMonitorConfig().getLoginPassword());
        if (StringUtils.isBlank(result)) {
            // û������,����?
            this.logger.warn("û�пͻ�������,meta: " + sender.getServerUrl());
            return null;
        }

        final AtomicInteger connSum = new AtomicInteger(0);
        Utils.processEachLine(result, new Action() {

            @Override
            public void process(String line) {
                String[] tmp = StringUtils.split(line, " ");
                if (tmp != null && tmp.length == 2) {
                    int count = Integer.parseInt(tmp[0]);
                    connSum.addAndGet(count);
                    if (count >= MetaConnProber.this.getMonitorConfig().getMetaConnectionPerIpThreshold()) {
                        MetaConnProber.this.alert("�ͻ���[" + tmp[1] + "]��Meta������" + sender.getServerUrl() + "����������������["
                                + count + "]��.");
                    }
                }
            }
        });

        String msg = "�ͻ������ӵ�Meta������[" + sender.getServerUrl() + "]������������������[" + connSum.get() + "]��.";
        this.logger.debug(msg);
        if (connSum.get() >= MetaConnProber.this.getMonitorConfig().getMetaConnectionThreshold()) {
            this.alert(msg);
        }

        return null;
    }


    @Override
    protected void processResult(MonitorResult monitorResult) {
        // do nothing
    }

}