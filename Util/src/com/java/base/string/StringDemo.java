package com.java.base.string;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年8月18日下午2:29:07
 * @version 1.0
 * 下面代码 str1、str2、str4 引用了相同的地址，但是 str3 却重新开辟了一块内存空间，
 * 虽然 str3 单独占用了堆空间，但是它所指向的实体和 str1 完全一样。
 */
public class StringDemo {
	 public static void main(String[] args){
	 String str1 = "abc";
	 String str2 = "abc";
	 String str3 = new String("abc");
	 String str4 = str1;
	 System.out.println("is str1 = str2?"+(str1==str2));
	 System.out.println("is str1 = str3?"+(str1==str3));
	 System.out.println("is str1 refer to str3?"+(str1.intern()==str3.intern()));
	 System.out.println("is str1 = str4"+(str1==str4));
	 System.out.println("is str2 = str4"+(str2==str4));
	 System.out.println("is str4 refer to str3?"+(str4.intern()==str3.intern()));
	 }
	}
