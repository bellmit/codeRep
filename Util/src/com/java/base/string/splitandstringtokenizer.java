package com.java.base.string;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年8月18日下午2:42:19
 * @version 1.0
 */
import java.util.StringTokenizer;

public class splitandstringtokenizer {
	public static void main(String[] args) {
		String orgStr = null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 100000; i++) {
			sb.append(i);
			sb.append(",");
		}
		orgStr = sb.toString();
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			orgStr.split(",");
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);

		start = System.currentTimeMillis();
		String orgStr1 = sb.toString();
		StringTokenizer st = new StringTokenizer(orgStr1, ",");
		for (int i = 0; i < 100000; i++) {
			st.nextToken();
		}
		st = new StringTokenizer(orgStr1, ",");
		end = System.currentTimeMillis();
		System.out.println(end - start);

		start = System.currentTimeMillis();
		String orgStr2 = sb.toString();
		String temp = orgStr2;
		while (true) {
			String splitStr = null;
			int j = temp.indexOf(",");
			if (j < 0)
				break;
			splitStr = temp.substring(0, j);
			temp = temp.substring(j + 1);
		}
		temp = orgStr2;
		end = System.currentTimeMillis();
		System.out.println(end - start);
	}
}