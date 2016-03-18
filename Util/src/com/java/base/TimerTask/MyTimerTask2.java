package com.java.base.TimerTask;

import java.util.TimerTask;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年4月2日下午2:41:54
 * @version 1.0
 */
public class MyTimerTask2 extends TimerTask{
	private int count = 0;
	@Override
	public void run() {
		System.out.println("MyTimerTask2计数：" + count);
			System.out.println("MyTimerTask2 结束了！！");
			System.gc();
//			cancel();
	}
}
