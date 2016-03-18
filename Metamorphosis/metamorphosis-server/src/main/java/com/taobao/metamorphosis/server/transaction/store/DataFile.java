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
package com.taobao.metamorphosis.server.transaction.store;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * ������һ�������ļ�
 * 
 * @author dogun (yuexuqiang at gmail.com)
 */
class DataFile {
    private final int number;
    private final File file;
    private final AtomicInteger referenceCount = new AtomicInteger(0);
    protected FileChannel fc;
    protected RandomAccessFile raf;


    /**
     * ���캯�������ָ�����ļ������ҽ�ָ��ָ���ļ���β
     * 
     * @param file
     * @throws IOException
     */
    DataFile(final File file) throws IOException {
        this(file, 0, false);
    }


    DataFile(final File file, final int number) throws IOException {
        this(file, number, false);
    }


    int getNumber() {
        return this.number;
    }


    /**
     * ���캯�������ָ�����ļ������ҽ�ָ��ָ���ļ���β
     * 
     * @param file
     * @throws IOException
     */
    DataFile(final File file, final int number, final boolean force) throws IOException {
        this.file = file;
        this.number = number;
        this.raf = new RandomAccessFile(file, force ? "rws" : "rw");
        this.fc = this.raf.getChannel();
        // ָ���Ƶ����
        this.fc.position(this.fc.size());
    }


    /**
     * ����ļ��Ĵ�С
     * 
     * @return �ļ��Ĵ�С
     * @throws IOException
     */
    long getLength() throws IOException {
        return this.fc.size();
    }


    void truncate(final long size) throws IOException {
        this.fc.truncate(size);
    }


    /**
     * ��ȡ�ļ�����޸�ʱ��
     * 
     * @return
     * @throws IOException
     */
    long lastModified() throws IOException {
        return this.file.lastModified();
    }


    /**
     * ɾ���ļ�
     * 
     * @return �Ƿ�ɾ���ɹ�
     * @throws IOException
     */
    boolean delete() throws IOException {
        this.close();
        return this.file.delete();
    }


    /**
     * ǿ�ƽ�����д��Ӳ��
     * 
     * @throws IOException
     */
    void force() throws IOException {
        if (this.fc.isOpen()) {
            this.fc.force(false);
        }
    }


    /**
     * �ر��ļ�
     * 
     * @throws IOException
     */
    void close() throws IOException {
        try {
            if (this.fc != null) {
                this.fc.close();
            }
        }
        finally {
            if (this.raf != null) {
                this.raf.close();
            }
        }
    }


    /**
     * ���ļ���ȡ���ݵ�bf��ֱ���������߶����ļ���β�� <br />
     * �ļ���ָ�������ƶ�bf�Ĵ�С
     * 
     * @param bf
     * @throws IOException
     */
    void read(final ByteBuffer bf) throws IOException {
        while (bf.hasRemaining()) {
            final int l = this.fc.read(bf);
            if (l < 0) {
                break;
            }
        }
    }


    /**
     * ���ļ����ƶ�λ�ö�ȡ���ݵ�bf��ֱ���������߶����ļ���β�� <br />
     * �ļ�ָ�벻���ƶ�
     * 
     * @param bf
     * @param offset
     * @throws IOException
     */
    void read(final ByteBuffer bf, final long offset) throws IOException {
        int size = 0;
        while (bf.hasRemaining()) {
            final int l = this.fc.read(bf, offset + size);
            size += l;
            if (l < 0) {
                break;
            }
        }
    }


    /**
     * д��bf���ȵ����ݵ��ļ����ļ�ָ�������ƶ�
     * 
     * @param bf
     * @return д�����ļ�position
     * @throws IOException
     */
    long write(final ByteBuffer bf) throws IOException {
        while (bf.hasRemaining()) {
            final int l = this.fc.write(bf);
            if (l < 0) {
                break;
            }
        }
        return this.fc.position();
    }


    /**
     * ��ָ��λ��д��bf���ȵ����ݵ��ļ����ļ�ָ��<b>����</b>����ƶ�
     * 
     * @param offset
     * @param bf
     * @throws IOException
     */
    void write(final long offset, final ByteBuffer bf) throws IOException {
        int size = 0;
        while (bf.hasRemaining()) {
            final int l = this.fc.write(bf, offset + size);
            size += l;
            if (l < 0) {
                break;
            }
        }
    }


    /**
     * ���ļ�����һ�����ü���
     * 
     * @return ���Ӻ�����ü���
     */
    int increment() {
        return this.referenceCount.incrementAndGet();
    }


    /**
     * �����ļ�����
     * 
     * @param i
     */
    void setReferenceCount(final int i) {
        this.referenceCount.set(i);
    }


    /**
     * ���ļ�����һ�����ü���
     * 
     * @return ���ٺ�����ü���
     */
    int decrement() {
        return this.referenceCount.decrementAndGet();
    }


    /**
     * �ļ��Ƿ���ʹ�ã����ü����Ƿ���0�ˣ�
     * 
     * @return �ļ��Ƿ���ʹ��
     */
    boolean isUnUsed() {
        return this.getReferenceCount() <= 0;
    }


    long position() throws IOException {
        return this.fc.position();
    }


    /**
     * ������ü�����ֵ
     * 
     * @return ���ü�����ֵ
     */
    int getReferenceCount() {
        return this.referenceCount.get();
    }


    @Override
    public String toString() {
        String result = null;
        try {
            result =
                    this.file.getName() + " , length = " + this.getLength() + " refCount = " + this.referenceCount
                            + " position:" + this.fc.position();
        }
        catch (final IOException e) {
            result = e.getMessage();
        }
        return result;
    }
}