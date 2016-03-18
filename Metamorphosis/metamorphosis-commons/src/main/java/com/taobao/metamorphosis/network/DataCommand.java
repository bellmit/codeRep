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
package com.taobao.metamorphosis.network;

import com.taobao.gecko.core.buffer.IoBuffer;


/**
 * Ӧ�����Э���ʽ���£� value total-length opaque\r\n data,����data�Ľṹ���£�
 * <ul>
 * <li>4���ֽڵ���Ϣ���ݳ��ȣ����ܰ������ԣ�</li>
 * <li>4���ֽڵ�check sum</li>
 * <li>8���ֽڵ���Ϣid</li>
 * <li>4���ֽڵ�flag</li>
 * <li>��Ϣ���ݣ���������ԣ���Ϊ��
 * <ul>
 * <li>4���ֽڵ����Գ���+ ��Ϣ���� + payload</li>
 * </ul>
 * </li> ����Ϊ��
 * <ul>
 * <li>payload</li>
 * <ul>
 * </li>
 * </ul>
 * 
 * @author boyan
 * @Date 2011-4-19
 * 
 */
public class DataCommand extends AbstractResponseCommand {
    private final byte[] data;
    static final long serialVersionUID = -1L;


    public byte[] getData() {
        return this.data;
    }


    public DataCommand(final byte[] data, final Integer opaque) {
        super(opaque);
        this.data = data;
    }


    @Override
    public boolean isBoolean() {
        return false;
    }


    @Override
    public IoBuffer encode() {
        // �����κ����飬����data command��transferTo���
        return null;
    }

}