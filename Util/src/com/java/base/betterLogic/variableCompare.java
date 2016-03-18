package com.java.base.betterLogic;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a> 2015��8��18������2:55:23
 * @version 1.0
 * ���÷���ʱ���ݵĲ����Լ��ڵ����д�������ʱ������������ջ (Stack) ���棬��д�ٶȽϿ졣
 * ����������羲̬������ʵ������ȣ����ڶ� (heap) �д�������д�ٶȽ���
 * �嵥 12 ��ʾ������ʾ��ʹ�þֲ������;�̬�����Ĳ���ʱ��Աȡ�
 */
public class variableCompare {
	public static int b = 0;

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		int a = 0;
		long starttime = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			a++;// �ں������ڶ���ֲ�����
		}
		System.out.println(System.currentTimeMillis() - starttime);

		starttime = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			b++;// �ں������ڶ��徲̬����
		}
		System.out.println(System.currentTimeMillis() - starttime);
	}
}
