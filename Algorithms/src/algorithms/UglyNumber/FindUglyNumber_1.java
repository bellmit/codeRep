package algorithms.UglyNumber;

import java.util.Scanner;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��7��8������3:51:25
 * @version 1.0
 * ���ǰ�ֻ������2��3��5�����������Ugly Number����
 * ����6��8���ǳ���14���ǣ���Ϊ�������7��
 * ϰ�������ǰ�1�����ǵ�һ������
 * �󰴴�С�����˳��ĵ�1500������
 */
public class FindUglyNumber_1 {
//  Ѱ��һ�����ǲ�������ĳ��������ˮ����ȣ���򵥵ķ������Ǳ���
//  ��������һ������ض�����д��2^m*3^n*5^p��������һ������ֻ����2��3��5���ӣ�
//	Ҳ����ζ�Ÿ���number%2==0��number%3==0��number%5==0�����һ�����ܱ�2���
//	���Ǿ��������2���ܱ�3������Ǿ��������3���ܱ�5������Ǿ��������5��
//	������õ�1��������ǳ���
	//�ж��Ƿ�Ϊ����
	public boolean isUglyNumber(int number) {
		while(number % 2 == 0) {
			number = number / 2;
		}
		while(number % 3 == 0) {
			number = number / 3;
		}
		while(number % 5 == 0) {
			number = number / 5;
		}
		return number == 1 ? true : false;
	}
	
	public int getUglyNumber(int index) {
		if(index < 1) {
			return 0;
		}
		int count = 0;
		int number = 0;
		while(count < index) {
			++number;
			if(isUglyNumber(number)) {
				count ++;
			}
		}
		return number;
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int index = sc.nextInt();
		Long begin = System.currentTimeMillis();
		System.out.println(new FindUglyNumber_1().getUglyNumber(index));
		System.out.println("time:" + (System.currentTimeMillis() - begin) + "ms");
	}
}
