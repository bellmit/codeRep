package com.java.base.betterLogic;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��8��18������2:57:48
 * @version 1.0
 * λ���������е���������Ϊ��Ч�ġ���ˣ����Գ���ʹ��λ������沿���������㣬�����ϵͳ�������ٶȡ�
 * ����͵ľ��Ƕ��������ĳ˳������Ż���
 */
public class yunsuan {
	 public static void main(String args[]){
	 long start = System.currentTimeMillis();
	 long a=1000;
	 for(int i=0;i<10000000;i++){
	 a*=2;
	 a/=2;
	 }
	 System.out.println(a);
	 System.out.println(System.currentTimeMillis() - start);
	 start = System.currentTimeMillis();
	 for(int i=0;i<10000000;i++){
	 a<<=1;
	 a>>=1;
	 }
	 System.out.println(a);
	 System.out.println(System.currentTimeMillis() - start);
	 }
	}
