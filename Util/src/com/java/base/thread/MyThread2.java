package com.java.base.thread;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年8月20日下午3:16:59
 * @version 1.0
 */
public class MyThread2 extends Thread{
	public void run() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("myThread2.run");
		
	}
}
