package com.java.base.betterLogic;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��8��18������3:01:19
 * @version 1.0
 * ���δ���Ĳ������ȡ���ظ��Ĺ�ʽ��ʹ�������ʽ��ÿ��ѭ������ִֻ��һ�Ρ�
 * �ֱ��ʱ 202ms �� 110ms���ɼ���ȡ���ӵ��ظ��������൱��������ġ�������Ӹ������ǣ�
 * ��ѭ�����ڣ�����ܹ���ȡ��ѭ������ļ��㹫ʽ�������ȡ�������������ó��������ظ��ļ��㡣
 */
public class duplicatedCode {
	 @SuppressWarnings("unused")
	public static void beforeTuning(){
	 long start = System.currentTimeMillis();
	 double a1 = Math.random();
	 double a2 = Math.random();
	 double a3 = Math.random();
	 double a4 = Math.random();
	 double b1,b2;
	 for(int i=0;i<10000000;i++){
	 b1 = a1*a2*a4/3*4*a3*a4;
	 b2 = a1*a2*a3/3*4*a3*a4;
	 }
	 System.out.println(System.currentTimeMillis() - start);
	 }
	 
	 @SuppressWarnings("unused")
	public static void afterTuning(){
	 long start = System.currentTimeMillis();
	 double a1 = Math.random();
	 double a2 = Math.random();
	 double a3 = Math.random();
	 double a4 = Math.random();
	 double combine,b1,b2;
	 for(int i=0;i<10000000;i++){
	 combine = a1*a2/3*4*a3*a4;
	 b1 = combine*a4;
	 b2 = combine*a3;
	 }
	 System.out.println(System.currentTimeMillis() - start);
	 }
	 
	 public static void main(String[] args){
	 duplicatedCode.beforeTuning();
	 duplicatedCode.afterTuning();
	 }
	}
