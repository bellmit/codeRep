package algotithms.DiceTotal;

import java.util.Scanner;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��7��10������11:29:50
 * @version 1.0
 * ����ڷ���һ��Ч�ʸ�ߣ��÷����ÿռ任ʱ��
 */
public class DiceTotal_2 {
	private static int diceMaxValue = 6;
	public void print(int number) {
		double[][] total = new double[2][];
		total[0] = new double[number*diceMaxValue + 1];
		total[1] = new double[number*diceMaxValue + 1];
		
		for(int i=0;i<number*diceMaxValue+1;i++) {
			total[0][i] = 0;
			total[1][i] = 0;
		}
		
		int flag = 0;
		for(int i=1;i<=diceMaxValue;i++) {
			total[flag][i] = 1;
		}
		
		for(int k=2;k<=number;k++) {
			for(int s=k;s<=k*diceMaxValue;s++) {
				total[1-flag][s] = 0;
				for(int j=1;j<=diceMaxValue&&j<=s-k+1;j++) {
					total[1-flag][s] += total[flag][s-j];
				}
			}
			flag = 1-flag;
		}
		
		double totalSum = Math.pow(diceMaxValue, number);
		System.out.println("�ܺ�--------------����");
		for(int i=number;i<=number*diceMaxValue;i++) {
			double prop = total[flag][i]/totalSum;
			System.out.println(i+"--------------"+prop);
		}
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int number = sc.nextInt();
		Long begin = System.currentTimeMillis();
		new DiceTotal_2().print(number);
		System.out.println("time:" + (System.currentTimeMillis() - begin) + "ms");
	}
}
