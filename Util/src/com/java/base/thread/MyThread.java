package com.java.base.thread;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��8��20������3:12:12
 * @version 1.0
 */
public class MyThread implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(10000);
			System.out.println("MyThread!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
