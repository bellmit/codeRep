/**
 * tyread.com Inc.
 * Copyright (c) �����Ķ��Ļ��������޹�˾  All Rights Reserved.
 */
package com.java.base.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��3��27������8:13:29
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
