/**
 * user info center(uic)
 * tyread.com Inc.
 * Copyright (c) 天翼阅读文化传播有限公司  All Rights Reserved.
 */
package com.java.base.calendar;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author <a href="mailto:zhuhao.567@163.com.">朱豪</a>
 * 2014年12月10日上午10:36:14
 * @version 1.0
 */
public class Test {
	public static void main(String[] args) {
//		Calendar thisWeek = Calendar.getInstance();
//		System.out.println(thisWeek.get(Calendar.DAY_OF_WEEK));
//		thisWeek.add(5, -2);
//		System.out.println(thisWeek.getTime());
//		  //根据得到的请求参数，构建一个Calendar
//        Calendar c = Calendar.getInstance();
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH);
//        int date = c.get(Calendar.DAY_OF_MONTH);
//        System.out.println(year +" "+  month + " " + date);
//        // 注意月份的起始值为0而不是1，所以要设置八月时，我们用7而不是8
//        c.set(year, month , date);
//        System.out.println(c.getTime());
//        //得到输入的日期是一周的第几天
//        int dow = c.get(Calendar.DAY_OF_WEEK);
//        dow = (dow == 1)?7:(dow - 1);
//        System.out.println(dow);
//        //得到一周的第一天，也就是星期天的日期
//        c.set(year, month, date, 0, 0, 0);
//        c.add(Calendar.DATE, 1-dow);
//        
//        
//        
//        System.out.println(c.getTime().getClass());
//
//        System.out.println("得到的一周日期为");
//        for (int i = 0; i < 7; i++) {
//        	System.out.print(c.get(Calendar.DATE) + " ");
//            //继续使用Calendar的目的是为了防止跨月份的情况出现
//            c.add(Calendar.DATE, 1);
//        }
		
//		Calendar calendar = Calendar.getInstance();
//		Date week = Test.getWeek(calendar);
//		Date month = Test.getMonth(calendar);
//		Date year = Test.getYear(calendar);
//		//12小时制和24小时制的区别，小时小写hh是12制，大写HH是24制
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		dateFormat.format(week);
//		Timestamp timestamp = new Timestamp(week.getTime());
//		timestamp.setNanos(0);
//		System.out.println(timestamp);
//		dateFormat.format(month);
//		Timestamp timestamp1 = new Timestamp(month.getTime());
//		timestamp1.setNanos(0);
//		System.out.println(timestamp1);
//		dateFormat.format(year);
//		Timestamp timestamp2 = new Timestamp(year.getTime());
//		timestamp2.setNanos(0);
//		System.out.println(timestamp2);
//		Timestamp ss  = new Timestamp(System.currentTimeMillis());
//		System.out.println(ss);
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), 3,
				7, 0, 0, 0);
//		int dow = calendar.get(Calendar.DAY_OF_WEEK);
//		dow = (dow == 1)?7:(dow - 1);
//		calendar.add(Calendar.DATE, 1-dow);
//	        System.out.println(calendar.getTime());
			Timestamp timestamp = new Timestamp(calendar.getTime().getTime());
			timestamp.setNanos(0);
			System.out.println(timestamp);
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(cal.getTime());
			System.out.println("time:" + time);
			while(true) {
				
			}
	}
	
	public static Date getWeek(Calendar calendar) {
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		int dow = calendar.get(Calendar.DAY_OF_WEEK);
		dow = (dow == 1)?7:(dow - 1);
		calendar.add(Calendar.DATE, 1-dow);
	        System.out.println(calendar.getTime());
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateFormat.format(calendar);
			System.out.println("calender:" + calendar);
			Timestamp timestamp = new Timestamp(calendar.getTime().getTime());
			timestamp.setNanos(0);
			System.out.println(timestamp);
	        return calendar.getTime();
	}
	
	public static Date getMonth(Calendar calendar) {
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				1, 0, 0, 0);
	        System.out.println(calendar.getTime());
	        return calendar.getTime();
	}
	
	public static Date getYear(Calendar calendar) {
		calendar.set(calendar.get(Calendar.YEAR), 0, 1, 0, 0, 0);
	        System.out.println(calendar.getTime());
	        return calendar.getTime();
	}
	
}
