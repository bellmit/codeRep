package com.java.base.betterLogic;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a> 2015��8��18������3:05:58
 * @version 1.0
 * ���ݸ�����һ��ʹ��Ƶ�ʺܸߵĹ��ܣ�JDK ���ṩ��һ����Ч�� API ��ʵ������
 * System.arraycopy() ������ native ������ͨ�� native ����������Ҫ������ͨ�ĺ�����
 * ���ԣ����������ܿ��ǣ�����������У�Ӧ�����ܵ��� native ������
 * ArrayList �� Vector ����ʹ���� System.arraycopy ���������ݣ�
 * �ر���ͬһ������Ԫ�ص��ƶ�����ͬ����֮��Ԫ�صĸ��ơ�
 * arraycopy �ı������ô���������һ��ָ���һ�������еĶ�����¼��
 * �е�������������Ĵ�����ָ�� (LODSB��LODSW��LODSB��STOSB��STOSW��STOSB)��
 * ֻ��ָ��ͷָ�룬Ȼ��ʼѭ�����ɣ���ִ��һ��ָ�ָ��ͺ���һ��λ�ã�
 * �����������ݾ�ѭ�����ٴΡ������Ӧ�ó�������Ҫ�������鸴�ƣ�Ӧ��ʹ�����������
 * �������Լ�ʵ�֡�
 */
public class arrayCopyTest {
	public static void arrayCopy() {
		int size = 10000000;
		int[] array = new int[size];
		int[] arraydestination = new int[size];
		for (int i = 0; i < array.length; i++) {
			array[i] = i;
		}
		long start = System.currentTimeMillis();
		for (int j = 0; j > 1000; j++) {
			System.arraycopy(array, 0, arraydestination, 0, size);// ʹ�� System
																	// ����ı���
																	// arraycopy
																	// ��ʽ
		}
		System.out.println(System.currentTimeMillis() - start);
	}

	public static void arrayCopySelf() {
		int size = 10000000;
		int[] array = new int[size];
		int[] arraydestination = new int[size];
		for (int i = 0; i < array.length; i++) {
			array[i] = i;
		}
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < size; j++) {
				arraydestination[j] = array[j];// �Լ�ʵ�ֵķ�ʽ��������������ݻ�����ʽ
			}
		}
		System.out.println(System.currentTimeMillis() - start);
	}

	public static void main(String[] args) {
		arrayCopyTest.arrayCopy();
		arrayCopyTest.arrayCopySelf();
	}
}
