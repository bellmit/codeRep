package com.java.base.nio;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年8月14日下午3:27:36
 * @version 1.0
 */
import java.nio.ByteBuffer;

public class DirectBuffervsByteBuffer {
	public void DirectBufferPerform() {
		long start = System.currentTimeMillis();
		ByteBuffer bb = ByteBuffer.allocateDirect(500);// 分配 DirectBuffer
		for (int i = 0; i < 100000; i++) {
			for (int j = 0; j < 99; j++) {
				bb.putInt(j);
			}
			bb.flip();
			for (int j = 0; j < 99; j++) {
				bb.getInt(j);
			}
		}
		bb.clear();
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		start = System.currentTimeMillis();
		for (int i = 0; i < 20000; i++) {
			ByteBuffer b = ByteBuffer.allocateDirect(10000);// 创建 DirectBuffer
		}
		end = System.currentTimeMillis();
		System.out.println(end - start);
	}

	public void ByteBufferPerform() {
		long start = System.currentTimeMillis();
		ByteBuffer bb = ByteBuffer.allocate(500);// 分配 DirectBuffer
		for (int i = 0; i < 100000; i++) {
			for (int j = 0; j < 99; j++) {
				bb.putInt(j);
			}
			bb.flip();
			for (int j = 0; j < 99; j++) {
				bb.getInt(j);
			}
		}
		bb.clear();
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		start = System.currentTimeMillis();
		for (int i = 0; i < 20000; i++) {
			ByteBuffer b = ByteBuffer.allocate(10000);// 创建 ByteBuffer
		}
		end = System.currentTimeMillis();
		System.out.println(end - start);
	}

	public static void main(String[] args) {
		DirectBuffervsByteBuffer db = new DirectBuffervsByteBuffer();
		db.ByteBufferPerform();
		db.DirectBufferPerform();
		System.out.println(((Integer)db.hashCode()).byteValue());
		int CPUcount = Runtime.getRuntime().availableProcessors();
		System.out.println(CPUcount);
	}
	
}
