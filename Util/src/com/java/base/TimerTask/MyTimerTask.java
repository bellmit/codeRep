package com.java.base.TimerTask;

import java.lang.reflect.Field;
import java.util.TimerTask;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年4月2日下午2:23:37
 * @version 1.0
 */
public class MyTimerTask extends TimerTask{
	private int count = 0;
	@Override
	public void run() {
		System.out.println("MyTimerTask计数：" + count++);
			System.out.println("MyTimerTask 结束了！！");
			System.gc();
//			cancel();

	}
}
