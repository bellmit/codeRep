/**
 * user info center(uic)
 * tyread.com Inc.
 * Copyright (c) �����Ķ��Ļ��������޹�˾  All Rights Reserved.
 */
package util.xuanhuan;

/**
 * @author <a href="mailto:zhuhao.567@163.com.">���</a>
 * 2014��12��9������9:48:23
 * @version 1.0
 */
public class XuanHuan {
	public static void main(String[] args) {
		for(int i = 110; i < 120; i++) {
				
				System.out.println("select DATE_FORMAT(create_time," + "\"%Y%m%d\"" + ") date,count(*) from vsop_user_info_" + i + " where create_time > " +"\"2015-06-27 00:00:00\""  + "and create_time < " + "\"2015-07-28 00:00:00\"" + "group by date;");
		}
		
	}
}
