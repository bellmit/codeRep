package com.java.base.dayuhao;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年8月12日下午3:36:43
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
		
//		j>>>i 与 j/(int)(Math.pow(2,i))的结果相同，其中i和j是整形。
		int c = 15 >>> 3;
		int d = 15/(int)(Math.pow(2,3));
		System.out.println(c);
		System.out.println(d);
	}

}
