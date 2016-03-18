/**
 * tyread.com Inc.
 * Copyright (c) 天翼阅读文化传播有限公司  All Rights Reserved.
 */
package com.java.base.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年3月27日下午8:13:29
 * @version 1.0
 */
public class Test {
	public static void main(String[] args) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Class c = Class.forName("reflect.test.Person");
		Constructor con = c.getDeclaredConstructor(String.class,Long.class);
		Person person = (Person)con.newInstance("zh",1L);
		System.out.println(person.getName());
	}
}
