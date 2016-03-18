package com.java.base.nio;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��8��14������3:28:40
 * @version 1.0
 */
import java.lang.reflect.Field;

public class monDirectBuffer {

	public static void main(String[] args) {
		try {
			Class c = Class.forName("java.nio.Bits");// ͨ������ȡ��˽������
			Field maxMemory = c.getDeclaredField("maxMemory");
			maxMemory.setAccessible(true);
			Field reservedMemory = c.getDeclaredField("reservedMemory");
			reservedMemory.setAccessible(true);
			synchronized (c) {
				Long maxMemoryValue = (Long) maxMemory.get(null);
				Long reservedMemoryValue = (Long) reservedMemory.get(null);
				System.out.println("maxMemoryValue=" + maxMemoryValue);
				System.out
						.println("reservedMemoryValue=" + reservedMemoryValue);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}
}
