package com.java.base.thread;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��8��21������2:18:35
 * @version 1.0
 */
public class ReturnThread extends Thread{
	private String value1;
	private String value2;
	
	public void run() {
		value1 = "1";
		value2 = "2";
	}
	
	public static void main(String[] args) {
		ReturnThread thread = new ReturnThread();
		thread.start();
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("value1:" + thread.value1);
		System.out.println("value2:" + thread.value2);
	}
}
