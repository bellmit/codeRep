package com.shellSort;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a> 2015��7��28������2:13:41
 * @version 1.0
 * ϣ��������С��������
 * ����˼�룺�㷨�Ƚ�Ҫ�����һ������ĳ������d��n/2,nΪҪ�������ĸ������ֳ������飬ÿ���м�¼���±����d.��ÿ����ȫ��Ԫ�ؽ���ֱ�Ӳ�������
 * ��Ȼ������һ����С��������d/2���������з��飬��ÿ�����ٽ���ֱ�Ӳ������򡣵���������1ʱ������ֱ�Ӳ��������������ɡ�
 */
public class shellSort {
	public shellSort() {
		int a[] = { 1, 54, 6, 3, 78, 34, 12, 45, 56, 100 };
		double d1 = a.length;
		int temp = 0;
		while (true) {
			d1 = Math.ceil(d1 / 2);
			int d = (int) d1;
			for (int x = 0; x < d; x++) {
				for (int i = x + d; i < a.length; i += d) {
					int j = i - d;
					temp = a[i];
					for (; j >= 0 && temp < a[j]; j -= d) {
						a[j + d] = a[j];
					}
					a[j + d] = temp;
				}
			}
			if (d == 1)
				break;
		}
		for (int i = 0; i < a.length; i++)
			System.out.print(a[i] + " ");
	}
	
	public static void main(String[] args) {
		new shellSort();
	}
}
