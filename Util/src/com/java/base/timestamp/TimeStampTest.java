/**
 * tyread.com Inc.
 * Copyright (c) 天翼阅读文化传播有限公司  All Rights Reserved.
 */
package com.java.base.timestamp;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * @author <a href="mailto:zhuhao.567@163.com.">朱豪</a>
 * 2015年1月27日下午2:28:37
 * @version 1.0
 */
public class TimeStampTest {
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		System.out.println(calendar.getTime());
		System.out.println("hour:" + calendar.get(Calendar.HOUR_OF_DAY));
		calendar.set(calendar.get(Calendar.YEAR)+2,0,1);
		System.out.println(new java.sql.Date(calendar.getTimeInMillis()));
		System.out.println(calendar.get(Calendar.YEAR) - 1);
		calendar.set(calendar.get(Calendar.YEAR), 0, 1, 0, 0, 0);
		System.out.println(calendar.getTime());
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Timestamp timestamp2 = new Timestamp(System.currentTimeMillis() + 30 * 60 *1000L);
		System.out.println(timestamp.after(calendar.getTime()));
		System.out.println(5);
		System.out.println(timestamp);
		System.out.println(timestamp2);
		Date date = new Date();
		System.out.println(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		System.out.println("y" + year + "m" + month);
		
		
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(calendar.get(Calendar.YEAR), 4, 1, 0, 0, 0);
		Timestamp aTimestamp = new Timestamp(calendar1.getTime().getTime());
		System.out.println("a" + aTimestamp);
		
		int i = calendar1.getActualMaximum(Calendar.DAY_OF_MONTH);
		System.out.println(i);
		
		
		Calendar beginCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		beginCal.set(beginCal.get(Calendar.YEAR), beginCal.get(Calendar.MONTH), 1, 0, 0, 0);
		Timestamp begin = new Timestamp(beginCal.getTime().getTime());
		endCal.set(endCal.get(Calendar.YEAR), endCal.get(Calendar.MONTH), endCal.getActualMaximum(Calendar.DAY_OF_MONTH), 11, 59, 59);
		Timestamp end = new Timestamp(endCal.getTime().getTime());
		
		System.out.println(begin);
		System.out.println(end);

		Timestamp ts = Timestamp.valueOf("2015-07-01 00:00:00");
		System.out.println("ts" + ts);
		
		
	}
}
