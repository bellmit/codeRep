package dp.strategy;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��6��16������3:15:31
 * @version 1.0
 */
public class Client {
	public static void main(String[] args) {
		Context bubble = new Context(new BubbleSort());
		System.out.println("ִ��ð������");
		bubble.sort();
		System.out.println("--------------");
		Context quick = new Context(new QuickSort());
		System.out.println("ִ�п�������");
		quick.sort();
		System.out.println("--------------");
	}
}
