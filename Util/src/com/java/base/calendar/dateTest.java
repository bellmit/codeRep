package com.java.base.calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年10月21日下午2:15:14
 * @version 1.0
 */
public class dateTest {
	public static void main(String[] args) {
		Calendar now = Calendar.getInstance();
		Date old = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
		System.out.println(old);
		Calendar oldCal = Calendar.getInstance();
		oldCal.setTime(old);
		oldCal.add(Calendar.DAY_OF_MONTH, 7);
		System.out.println(oldCal.getTime());
		System.out.println(now.getTimeInMillis() - oldCal.getTimeInMillis());
		
		 SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		 Date sDate = new Date();
		 String strDate=df.format(sDate);
		 System.out.println(strDate);
	}
}
