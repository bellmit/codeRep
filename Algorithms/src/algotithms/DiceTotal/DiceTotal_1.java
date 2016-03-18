package algotithms.DiceTotal;

import java.util.Scanner;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��7��10������10:18:40
 * @version 1.0
 */
public class DiceTotal_1 {
	private static int diceMaxValue = 6;
	public int fun(int diceTotalSum, int diceNumber) {
		if(diceNumber < 1) {
			return 0;
		}
		if(diceNumber == 1) {
			if(diceTotalSum >= diceNumber && diceTotalSum <= diceMaxValue * diceNumber) {
				return 1;
			}
			else {
				return 0;
			}
		}
		if(diceNumber > 1) {
			int sum = 0;
			for(int i=1;i<=6;i++) {
				sum += fun(diceTotalSum-i, diceNumber-1);
			}
			return sum;
		}
		return 0;
	}
	
	public int pow(int diceMax,int diceNumber) {
		int total = (int) Math.pow(diceMax, diceNumber);
		return total;
	}
	
	public void print(int diceNumber) {
		int total = pow(diceMaxValue, diceNumber);
		System.out.println("�ܺ�--------------����");
		for(int i=diceNumber;i<=diceNumber*diceMaxValue;i++) {
			float sum = fun(i, diceNumber);
			float prop = sum/total;
			System.out.println(i+"--------------"+prop);
		}
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int number = sc.nextInt();
		Long begin = System.currentTimeMillis();
		new DiceTotal_1().print(number);
		System.out.println("time:" + (System.currentTimeMillis() - begin) + "ms");
	}
}
