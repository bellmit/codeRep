package com.java.base.TimerTask;

import java.lang.reflect.Field;
import java.util.TimerTask;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��4��2������2:23:37
 * @version 1.0
 */
public class MyTimerTask extends TimerTask{
	private int count = 0;
	@Override
	public void run() {
		System.out.println("MyTimerTask������" + count++);
			System.out.println("MyTimerTask �����ˣ���");
			System.gc();
//			cancel();

	}
}
