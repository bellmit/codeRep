package com.java.base.string;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年8月18日下午2:47:40
 * @version 1.0
 */
public class StringConcat {
	 public static void main(String[] args){
	 String str = null;
	 String result = "";
	 
	 long start = System.currentTimeMillis();
	 for(int i=0;i<10000;i++){
	 str = str + i;
	 }
	 long end = System.currentTimeMillis();
	 System.out.println(end-start);
	 
	 start = System.currentTimeMillis();
	 for(int i=0;i<10000;i++){
	 result = result.concat(String.valueOf(i));
	 }
	 end = System.currentTimeMillis();
	 System.out.println(end-start);
	 
	 start = System.currentTimeMillis();
	 StringBuilder sb = new StringBuilder();
	 for(int i=0;i<10000;i++){
	 sb.append(i);
	 }
	 end = System.currentTimeMillis();
	 System.out.println(end-start);
	 }
	}
