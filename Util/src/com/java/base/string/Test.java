/**
 * user info center(uic)
 * tyread.com Inc.
 * Copyright (c) �����Ķ��Ļ��������޹�˾  All Rights Reserved.
 */
package com.java.base.string;


/**
 * @author <a href="mailto:zhuhao.567@163.com.">���</a>
 * 2014��12��11������2:13:01
 * @version 1.0
 */
public class Test {
	
	public static void main(String[] args) {
		String s = "version1: {remark:���ֶһ�; log_type:1; taget_id:1001121; target_count:2; target_desc:2��500�ĵ�ȯ;}";
		String english = "english";
		System.out.println(s.length());
		System.out.println(english.length());
		
		Long tr = 1000001L;
		System.out.println(tr==1000001);
		
		String remark = "����".toUpperCase();
		System.out.println(("".equals(remark)?"null":remark));
		
		String paramValue = "12.34";
		 String exFileName = paramValue.substring(paramValue
                 .lastIndexOf(".") + 1);
		 System.out.println(paramValue.lastIndexOf("."));
		 System.out.println(exFileName);
	}
	
}
