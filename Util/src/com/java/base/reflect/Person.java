/**
 * tyread.com Inc.
 * Copyright (c) 天翼阅读文化传播有限公司  All Rights Reserved.
 */
package com.java.base.reflect;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年3月27日下午8:12:39
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
