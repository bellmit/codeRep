/**
 * tyread.com Inc.
 * Copyright (c) �����Ķ��Ļ��������޹�˾  All Rights Reserved.
 */
package com.java.base.reflect;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��3��27������8:12:39
 * @version 1.0
 */
public class Person {
	private String name;
	private Long age;
	public Person(String name,Long age) {
		this.name = name;
		this.age = age;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the age
	 */
	public Long getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(Long age) {
		this.age = age;
	}
	
}
