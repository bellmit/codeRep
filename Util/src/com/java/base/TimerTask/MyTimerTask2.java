package com.java.base.TimerTask;

import java.util.TimerTask;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��4��2������2:41:54
 * @version 1.0
 */
public class MyTimerTask2 extends TimerTask{
	private int count = 0;
	@Override
	public void run() {
		System.out.println("MyTimerTask2������" + count);
			System.out.println("MyTimerTask2 �����ˣ���");
			System.gc();
//			cancel();
	}
}
