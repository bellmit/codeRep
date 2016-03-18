package com.java.base.calendar;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年9月2日上午11:30:49
 * @version 1.0
 */
public class calendarTest {
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		System.out.println(year + " " + month + " " + day);
		calendar.setTimeInMillis(calendar.getTimeInMillis() - 3 * 24 * 60 * 60 * 1000);
		int year1 = calendar.get(Calendar.YEAR);
		int month1 = calendar.get(Calendar.MONTH);
		int day1 = calendar.get(Calendar.DAY_OF_MONTH);
		System.out.println(year1 + " " + month1 + " " + day1);
		
		String str = "9";
		if(str.length() == 1) {
			str = "0" + str;
		}
		System.out.println(str);
		
		
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTimeInMillis(calendar1.getTimeInMillis() - 2 * 24 * 60 * 60 * 1000);
		String year2 = String.valueOf(calendar1.get(Calendar.YEAR));
		String month2 = String.valueOf(calendar1.get(Calendar.MONTH) + 1);
		String day2 = String.valueOf(calendar1.get(Calendar.DAY_OF_MONTH));
		if(1 == month2.length() ) {
			month2 = "0" + month2;
		}
		if(1 == day2.length() ) {
			day2 = "0" + day2;
		}
		System.out.println(year2 + month2 + day2);
		
		String test = null;
		int a = 0;
		while(null == test && a < 3) {
			if(a == 2) {
				test = "1";
			}
			if(null != test) {
				break;
			}
			a++;
		}
		System.out.println(test);
	}
}
