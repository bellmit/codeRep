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

import java.nio.ByteBuffer;
import java.util.List;

import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.MessageAccessor;
import com.taobao.metamorphosis.exception.InvalidMessageException;
import com.taobao.metamorphosis.network.ByteUtils;
import com.taobao.metamorphosis.network.PutCommand;


public class MessageUtils {

    public static class DecodedMessage {
        public final int newOffset;
        public final Message message;


        public DecodedMessage(final int newOffset, final Message message) {
            super();
            this.newOffset = newOffset;
            this.message = message;
        }

    }


    /**
     * ������Ϣbuffer��ʵ�ʴ洢�ڷ������Ľṹ���£�
     * <ul>
     * <li>message length(4 bytes),including attribute and payload</li>
     * <li>checksum(4 bytes)</li>
     * <li>message id(8 bytes)</li>
     * <li>message flag(4 bytes)</li>
     * <li>attribute length(4 bytes) + attribute,not nessary</li>
     * <li>payload</li>
     * </ul>
     * 
     * @param req
     * @return
     */
    public static ByteBuffer makeMessageBuffer(final long msgId, final PutCommand req) {
        // message length + checksum + id +flag + data
        final ByteBuffer buffer = ByteBuffer.allocate(4 + 4 + 8 + 4 + req.getData().length);
        buffer.putInt(req.getData().length);
        buffer.putInt(CheckSum.crc32(req.getData()));
        buffer.putLong(msgId);
        buffer.putInt(req.getFlag());
        buffer.put(req.getData());
        buffer.flip();
        return buffer;
    }


    public static ByteBuffer makeMessageBuffer(final List<Long> msgIds, final List<PutCommand> reqs) {
        if (msgIds == null || reqs == null) {
            throw new IllegalArgumentException("Null id list or request list");
        }
        if (msgIds.size() != reqs.size()) {
            throw new IllegalArgumentException("id list is not adapte to request list");
        }
        int capacity = 0;
        for (final PutCommand req : reqs) {
            capacity += 4 + 4 + 8 + 4 + req.getData().length;
        }
        final ByteBuffer buffer = ByteBuffer.allocate(capacity);
        for (int i = 0; i < reqs.size(); i++) {
            final PutCommand req = reqs.get(i);
            final long msgId = msgIds.get(i);
            buffer.putInt(req.getData().length);
            buffer.putInt(CheckSum.crc32(req.getData()));
            buffer.putLong(msgId);
            buffer.putInt(req.getFlag());
            buffer.put(req.getData());
        }
        buffer.flip();
        return buffer;
    }


    /**
     * ��binary�����н����Ϣ
     * 
     * @param topic
     * @param data
     * @param offset
     * @return
     * @throws InvalidMessageException
     */
    public static DecodedMessage decodeMessage(final String topic, final byte[] data, final int offset)
            throws InvalidMessageException {
        final ByteBuffer buf = ByteBuffer.wrap(data, offset, HEADER_LEN);
        final int msgLen = buf.getInt();
        final int checksum = buf.getInt();
        vailidateMessage(offset + HEADER_LEN, msgLen, checksum, data);
        final long id = buf.getLong();
        // ȡflag
        final int flag = buf.getInt();
        String attribute = null;
        int payLoadOffset = offset + HEADER_LEN;
        int payLoadLen = msgLen;
        if (payLoadLen > MAX_READ_BUFFER_SIZE) {
            throw new InvalidMessageException("Too much long payload length:" + payLoadLen);
        }
        // ��������ԣ���Ҫ��������
        if (MessageFlagUtils.hasAttribute(flag)) {
            // ȡ4���ֽڵ����Գ���
            final int attrLen = getInt(offset + HEADER_LEN, data);
            // ȡ��Ϣ����
            final byte[] attrData = new byte[attrLen];
            System.arraycopy(data, offset + HEADER_LEN + 4, attrData, 0, attrLen);
            attribute = ByteUtils.getString(attrData);
            // ����payloadOffset������4���ֽڵ���Ϣ���Ⱥ���Ϣ���ȱ���
            payLoadOffset = offset + HEADER_LEN + 4 + attrLen;
            // payload���ȵݼ�����ȥ4���ֽڵ���Ϣ���Ⱥ���Ϣ���ȱ���
            payLoadLen = msgLen - 4 - attrLen;
        }
        // ��ȡpayload
        final byte[] payload = new byte[payLoadLen];
        System.arraycopy(data, payLoadOffset, payload, 0, payLoadLen);
        final Message msg = new Message(topic, payload);
        MessageAccessor.setFlag(msg, flag);
        msg.setAttribute(attribute);
        MessageAccessor.setId(msg, id);
        return new DecodedMessage(payLoadOffset + payLoadLen, msg);
    }


    /**
     * У��checksum
     * 
     * @param msg
     * @param checksum
     */
    public static void vailidateMessage(final int offset, final int msgLen, final int checksum, final byte[] data)
            throws InvalidMessageException {
        if (checksum != CheckSum.crc32(data, offset, msgLen)) {
            throw new InvalidMessageException("Invalid message");
        }
    }


    public static int getInt(final int offset, final byte[] data) {
        return ByteBuffer.wrap(data, offset, 4).getInt();
    }

    /**
     * 20���ֽڵ�ͷ�� *
     * <ul>
     * <li>message length(4 bytes),including attribute and payload</li>
     * <li>checksum(4 bytes)</li>
     * <li>message id(8 bytes)</li>
     * <li>message flag(4 bytes)</li>
     * </ul>
     */
    public static final int HEADER_LEN = 20;
    public static final int MAX_READ_BUFFER_SIZE = Integer.parseInt(System.getProperty(
        "notify.remoting.max_read_buffer_size", "2097152"));

}