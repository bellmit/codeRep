package algorithms.Fibonacci;

import java.util.Scanner;

/**
 * @author hzzhuhao1
 */
public class Fibonacci_1 {
	public Long Fibonacci(int number) {
		if(0 == number) {
			return 0L;
		} else if(1 == number) {
			return 1L;
		} else {
			return Fibonacci(number - 2) + Fibonacci(number - 1);
		}
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int number = sc.nextInt();
		Long begin = System.currentTimeMillis();
		System.out.println(new Fibonacci_1().Fibonacci(number));
		System.out.println("time:" + (System.currentTimeMillis() - begin) + "ms");
	}
}
