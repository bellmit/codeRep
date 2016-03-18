package com.java.base.betterLogic;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a> 2015年8月18日下午3:05:58
 * @version 1.0
 * 数据复制是一项使用频率很高的功能，JDK 中提供了一个高效的 API 来实现它。
 * System.arraycopy() 函数是 native 函数，通常 native 函数的性能要优于普通的函数，
 * 所以，仅处于性能考虑，在软件开发中，应尽可能调用 native 函数。
 * ArrayList 和 Vector 大量使用了 System.arraycopy 来操作数据，
 * 特别是同一数组内元素的移动及不同数组之间元素的复制。
 * arraycopy 的本质是让处理器利用一条指令处理一个数组中的多条记录，
 * 有点像汇编语言里面的串操作指令 (LODSB、LODSW、LODSB、STOSB、STOSW、STOSB)，
 * 只需指定头指针，然后开始循环即可，即执行一次指令，指针就后移一个位置，
 * 操作多少数据就循环多少次。如果在应用程序中需要进行数组复制，应该使用这个函数，
 * 而不是自己实现。
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
			System.arraycopy(array, 0, arraydestination, 0, size);// 使用 System
																	// 级别的本地
																	// arraycopy
																	// 方式
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
				arraydestination[j] = array[j];// 自己实现的方式，采用数组的数据互换方式
			}
		}
		System.out.println(System.currentTimeMillis() - start);
	}

	public static void main(String[] args) {
		arrayCopyTest.arrayCopy();
		arrayCopyTest.arrayCopySelf();
	}
}
