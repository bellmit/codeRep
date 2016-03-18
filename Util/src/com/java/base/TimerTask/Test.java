package com.java.base.TimerTask;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年4月2日下午2:21:24
 * @version 1.0
 */
public class Test {
	public static void main(String[] args) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Timer timer = new Timer();
		TimerTask timerTask = new MyTimerTask();
		TimerTask timerTask1 = new MyTimerTask();
		TimerTask timerTask2 = new MyTimerTask2();
		Long now = System.currentTimeMillis();
//		timer.schedule(timerTask, 1000L);
//		timer.schedule(timerTask2, 2000L);
//		  Field field = TimerTask.class.getDeclaredField("state");
//		  field.setAccessible(true);
//		  field.set(timerTask, 0);
//		  field.set(timerTask2, 0);
//		  
//		  timer.schedule(timerTask, 3000L);
//		  timer.schedule(timerTask2, 3000L);
//		int j =0;
//		int k = 1;
		for(int i=0;i<5;i++) {
//			Field field = TimerTask.class.getDeclaredField("state");
//			  field.setAccessible(true);
//			  field.set(timerTask, 0);
			timer.schedule(new MyTimerTask(), (i+1)*1000L);
//			 Field field = TimerTask.class.getDeclaredField("state");
//			  field.setAccessible(true);
//			  field.set(timerTask, 0);
//			  timer.schedule(timerTask1, (5+1)*1000L);
		}
		System.out.println("主线程先结束了！！");
	}
}
