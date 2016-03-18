package dp.proxy;

import java.util.Random;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��4��27������3:58:41
 * @version 1.0
 */
public class Bus implements Moveable{
	@Override
	public void move() {
		System.out.println("��ʼ��ʻ...");
		try {
			Thread.sleep(new Random().nextInt(1000));
			System.out.println("����ʻ��...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("����ʻ����...");
	}
}
