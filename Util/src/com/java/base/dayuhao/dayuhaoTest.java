package com.java.base.dayuhao;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��8��12������3:36:43
 * @version 1.0
 */
public class dayuhaoTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a = 16;
		int b = a >> 1;
		System.out.println(b);
		
//		j>>>i �� j/(int)(Math.pow(2,i))�Ľ����ͬ������i��j�����Ρ�
		int c = 15 >>> 3;
		int d = 15/(int)(Math.pow(2,3));
		System.out.println(c);
		System.out.println(d);
	}

}
