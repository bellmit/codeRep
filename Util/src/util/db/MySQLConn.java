/**
 * user info center(uic)
 * tyread.com Inc.
 * Copyright (c) 天翼阅读文化传播有限公司  All Rights Reserved.
 */
package util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author <a href="mailto:zhuhao.567@163.com.">朱豪</a>
 * 2014年11月11日下午2:48:08
 * @version 1.0
 */
public class MySQLConn {
	public static void main(String[] args) throws Exception {
		MySQLConn mySQLConn = new MySQLConn();
		mySQLConn.select("1");
	}
	public void select(String s) throws Exception{
		
		try {
			String dbid = "jdbc:mysql://localhost:3306/diamond";
			String drivername = "com.mysql.jdbc.Driver";
			String username = "zhuhao";
			String password = "123456";

			Class.forName(drivername);
			Connection con = DriverManager.getConnection(dbid,username,password); 
			con.setAutoCommit(false);
			Statement stmt = con.createStatement();
		String sql = "select * from config_item where id = 1;" ;
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()) {
			String name = rs.getString("data_id");
			System.out.println("name" + name);
		}
//			stmt.executeUpdate("delete from config_info where id = 2 ;");
//			stmt.executeUpdate("delete from config_info where id =  ;");
			
			
//		rs.close();
			con.commit();
			stmt.close();
			con.close();
			
			
//		System.out.println("yes");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
