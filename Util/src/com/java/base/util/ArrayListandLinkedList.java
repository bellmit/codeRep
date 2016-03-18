package com.java.base.util;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年8月12日下午2:38:56
 * @version 1.0
 */
import java.util.ArrayList;
import java.util.LinkedList;

public class ArrayListandLinkedList {
	public static void main(String[] args) {
		//ArrayList 是基于数组实现的，而数组是一块连续的内存空间，
        //如果在数组的任意位置插入元素，必然导致在该位置后的所有元素需要重新排列，
        //因此其效率较差，尽可能将数据插入到尾部。LinkedList 不会因为插入数据导致性能下降。
		long start = System.currentTimeMillis();
		ArrayList list = new ArrayList();
		Object obj = new Object();
		for (int i = 0; i < 5000000; i++) {
			list.add(obj);
		}
		long end = System.currentTimeMillis();
		System.out.println("ArrayList add 5000000 obj cost :" + (end - start) + "ms");

		start = System.currentTimeMillis();
		LinkedList list1 = new LinkedList();
		Object obj1 = new Object();
		for (int i = 0; i < 5000000; i++) {
			list1.add(obj1);
		}
		end = System.currentTimeMillis();
		System.out.println("LinkedList add 5000000 obj cost :" + (end - start) + "ms");

		start = System.currentTimeMillis();
		Object obj2 = new Object();
		for (int i = 0; i < 1000; i++) {
			list.add(0, obj2);
		}
		end = System.currentTimeMillis();
		System.out.println("ArrayList add 1000 obj in first index cost :" + (end - start) + "ms");

		start = System.currentTimeMillis();
		Object obj3 = new Object();
		for (int i = 0; i < 1000; i++) {
			list1.add(0,obj1);
		}
		end = System.currentTimeMillis();
		System.out.println("LinkedList add 1000 obj in first index cost :" + (end - start) + "ms");
		
//		ArrayList 的每一次有效的元素删除操作后都要进行数组的重组，
//		并且删除的元素位置越靠前，数组重组时的开销越大，要删除的元素位置越靠后，开销越小。
//		LinkedList 要移除中间的数据需要便利完半个 List。
		start = System.currentTimeMillis();
		list.remove(5000000);
		end = System.currentTimeMillis();
		System.out.println("ArrayList remove 5000001 index obj cost :" + (end - start) + "ms");

		start = System.currentTimeMillis();
		list1.remove(250000);
		end = System.currentTimeMillis();
		System.out.println("LinkedList remove 250001 index obj cost :" + (end - start) + "ms");

	}
}
