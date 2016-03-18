package com.java.base.thread;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��8��20������3:07:17
 * @version 1.0
 */
public class ThreadTest {
	
	public static void main(String[] args) {
		MyThread myThread = new MyThread();
		Thread thread = new Thread(myThread);
		thread.start();
		try {
			//threadִ����� �ټ�������ִ��
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("main Thread1");
		MyThread2 myThread2 = new MyThread2();
		myThread2.start();
		System.out.println("main thread2");
	}
}
