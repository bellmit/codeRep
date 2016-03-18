package com.java.base.util;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��8��12������2:38:56
 * @version 1.0
 */
import java.util.ArrayList;
import java.util.LinkedList;

public class ArrayListandLinkedList {
	public static void main(String[] args) {
		//ArrayList �ǻ�������ʵ�ֵģ���������һ���������ڴ�ռ䣬
        //��������������λ�ò���Ԫ�أ���Ȼ�����ڸ�λ�ú������Ԫ����Ҫ�������У�
        //�����Ч�ʽϲ�����ܽ����ݲ��뵽β����LinkedList ������Ϊ�������ݵ��������½���
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
		
//		ArrayList ��ÿһ����Ч��Ԫ��ɾ��������Ҫ������������飬
//		����ɾ����Ԫ��λ��Խ��ǰ����������ʱ�Ŀ���Խ��Ҫɾ����Ԫ��λ��Խ���󣬿���ԽС��
//		LinkedList Ҫ�Ƴ��м��������Ҫ�������� List��
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
