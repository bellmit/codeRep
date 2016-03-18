package com.java.base.random;

import java.util.Random;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年8月4日下午5:24:18
 * @version 1.0
 */
public class randomTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String userEmail = "zhuhao890@189read.com";
		String[] emailSplit = userEmail.split("@");
		String newUserEmail = emailSplit[0] + "_" + Math.abs(new Random().nextInt())%100 + "@" + emailSplit[1];
		System.out.println(newUserEmail);
		
		
		 Random random1 = new Random(100);
         System.out.println(random1.nextInt());
         System.out.println(random1.nextFloat());
         System.out.println(random1.nextBoolean());
         Random random2 = new Random(100);
         System.out.println(random2.nextInt());
         System.out.println(random2.nextFloat());
         System.out.println(random2.nextBoolean());
         
         
         Random random11 = new Random();
         for(int i = 0; i < 10;i++) {
             System.out.println(Math.abs(random11.nextInt())%5);
         }
	}

}
