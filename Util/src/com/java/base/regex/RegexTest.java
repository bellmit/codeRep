package com.java.base.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年12月1日下午2:29:20
 * @version 1.0
 */
public class RegexTest {
	public static void main(String[] args) {
		String text = "11:00\",\"sysCurTime\":\"2015-12-01 13:13:33\"}}";
		Pattern pattern = Pattern.compile(",\"sysCurTime\":\"(.*)\"}}");
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			System.out.println(matcher.group(1));
		}
	}
}
