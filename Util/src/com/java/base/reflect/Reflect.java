/**
 * tyread.com Inc.
 * Copyright (c) 天翼阅读文化传播有限公司  All Rights Reserved.
 */
package com.java.base.reflect;

import java.lang.reflect.Method;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年3月13日下午2:27:08
 * @version 1.0
 */
public class Reflect {
	String name;
	public Reflect() {
	}
	public String getName() {
		return name;
	}
	public static void main(String[] args) {
		Reflect reflect = new Reflect();
		Class<?> c = reflect.getClass();
		System.out.println(c.getName());
		try {
			Method method = c.getMethod("getName");
			System.out.println(method.toString());
			System.out.println(method.getName());
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
