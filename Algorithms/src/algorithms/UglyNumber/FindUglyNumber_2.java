package algorithms.UglyNumber;

import java.util.Scanner;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��7��8������4:27:23
 * @version 1.0
 */

/**����һ��������Ҫ�Ĳ������ڣ���һ����������ڲ��ǳ��������жϻ���ɴ�����ʱ���˷ѣ�
 * ����ܹ�����Ѿ�����õĳ���������һ������Ϳ��Ա������������ʵ�ִӳ������ĸ�Ч�㷨��
 * ��ݶ����֪������ĳ���϶���ǰ����֪�������2��3��5�õ��ġ�
 * 
 * ���Ǽ���һ���������Ѿ������ɳ�������Щ�����ǰ�˳�����еģ����ǰ����е��������Ϊmax��
 * ����һ������϶���ǰ��������2��3��5�õ��ġ��������ǳ���2�õ�����������ǰ������е�ÿһ�������2��
 * ����ԭ����������ģ���Ϊ����2��Ҳ����������ģ������Ȼ����һ����M2����ǰ���ÿһ������С�ڵ���max��
 * �����M2���ڵĺ�������Ǵ���max�ģ���Ϊ���ǻ���Ҫ���ֵ���˳����������ȡ��һ������max����M2��
 * ͬ����ڳ���3�����������ȡ��һ������max����M3�����ڳ���5�����������ȡ��һ������max����M5��
 * ������һ������ȡ:min{M2,M3,M5}����
 * 
 * ȡ��1��ʼ�ĵ�1500������
 * ����һ��ʱ��11565ms
 * ��������ʱ��37ms
 * ��������ʵ���ÿռ任��ʱ��
*/
public class FindUglyNumber_2 {
	public int Min(int num1,int num2,int num3) {
		int min = (num1 < num2) ? num1 : num2;
		min = (num3 < min) ? num3 : min;
		return min;
	}
	
	public int getUglyNumber(int index) {
		if(index < 1) {
			return 0;
		}
		int[] uglyNumbers = new int[index];
		uglyNumbers[0] = 1;
		int nextUglyIndex = 1;
		
		
		
		int M2 = uglyNumbers[0] * 2;
		int M3 = uglyNumbers[0] * 3;
		int M5 = uglyNumbers[0] * 5;
		
		while(nextUglyIndex < index) {
			int min = Min(M2, M3, M5);
			uglyNumbers[nextUglyIndex] = min;
			
			int[] multiply2 = uglyNumbers;
			int[] multiply3 = uglyNumbers;
			int[] multiply5 = uglyNumbers;
			int i = 0;
			M2 = multiply2[i] * 2;
			while(multiply2[i] * 2 <= uglyNumbers[nextUglyIndex] ) {
				i++;
				M2 = multiply2[i] * 2;
			}
			int j = 0;
			M3 = multiply3[j] * 3;
			while(multiply3[j] * 3 <= uglyNumbers[nextUglyIndex] ) {
				j++;
				M3 = multiply3[j] * 3;
			}
			int k = 0;
			M5 = multiply5[k] * 5;
			while(multiply5[k] * 5 <= uglyNumbers[nextUglyIndex] ) {
				k++;
				M5 = multiply5[k] * 5;
			}
			
			++ nextUglyIndex;
		}
		return uglyNumbers[nextUglyIndex - 1];
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int index = sc.nextInt();
		Long begin = System.currentTimeMillis();
		System.out.println(new FindUglyNumber_2().getUglyNumber(index));
		System.out.println("time:" + (System.currentTimeMillis() - begin) + "ms");
	}
}
