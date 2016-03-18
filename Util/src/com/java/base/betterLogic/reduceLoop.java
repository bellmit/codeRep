package com.java.base.betterLogic;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a> 2015��8��18������3:02:00
 * @version 1.0
 */
public class reduceLoop {
	public static void beforeTuning() {
		long start = System.currentTimeMillis();
		int[] array = new int[9999999];
		for (int i = 0; i < 9999999; i++) {
			array[i] = i;
		}
		System.out.println(System.currentTimeMillis() - start);
	}

	public static void afterTuning() {
		long start = System.currentTimeMillis();
		int[] array = new int[9999999];
		for (int i = 0; i < 9999999; i += 3) {
			array[i] = i;
			array[i + 1] = i + 1;
			array[i + 2] = i + 2;
		}
		System.out.println(System.currentTimeMillis() - start);
	}

	public static void main(String[] args) {
		reduceLoop.beforeTuning();
		reduceLoop.afterTuning();
	}
}
