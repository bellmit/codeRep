package com.java.base.thread;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年8月21日下午2:05:51
 * @version 1.0
 */
public class JoinThread extends Thread{
	public static int n = 0;
	static synchronized void inc() {
		n++;
	}
	public void run() {
		for(int i=0;i<10;i++) {
			try {
				inc();
				sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		Thread threads[] = new Thread[100];
		for(int i=0;i<threads.length;i++) {
			threads[i] = new JoinThread();
		}
		for(int i=0; i<threads.length;i++) {
			threads[i].start();
		}
		if(true) {
			for(int i=0; i<threads.length;i++) {
				try {
					threads[i].join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("n=" + JoinThread.n);
	}
}
