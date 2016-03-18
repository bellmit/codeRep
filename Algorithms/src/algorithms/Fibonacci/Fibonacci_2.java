package algorithms.Fibonacci;

import java.util.Scanner;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��7��9������9:58:19
 * @version 1.0
 * �÷����ȷ���һЧ�ʸߣ�ʱ�为���ΪO(n)
 */
public class Fibonacci_2 {
	public Long Fibonacci(int number) {
		if(0 == number) {
			return 0L;
		}
		if(1 == number) {
			return 1L;
		}
		Long firstItem = 0L;
		Long secondItem = 1L;
		Long fib = 0L;
		int count = 1;
		while(count < number) {
			fib = firstItem + secondItem;
			firstItem = secondItem;
			secondItem = fib;
			++count;
		}
		return fib;
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int number = sc.nextInt();
		Long begin = System.currentTimeMillis();
		System.out.println(new Fibonacci_2().Fibonacci(number));
		System.out.println("time:" + (System.currentTimeMillis() - begin) + "ms");
	}
}
