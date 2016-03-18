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



public class DBConn {

	public static void main(String[] args) throws Exception {
		DBConn dbConn = new DBConn();
		dbConn.select("1");
	}
	public void select(String s) throws Exception{
		
		
		Class.forName("oracle.jdbc.driver.OracleDriver");

		String url = "jdbc:oracle:thin:@172.23.0.32:1522:tyydtest";
		String username = "ytxt";
		String password = "ytxt123";

		Connection con = DriverManager.getConnection(url, username, password);
		Statement stmt = con.createStatement();
//		String sql = "select * from t_bookmark t where t.user_id = 200000000102611" ;
//		ResultSet rs = stmt.executeQuery(sql);
//		while(rs.next()) {
//			String name = rs.getString("bookmark_id");
//			System.out.println("name" + name);
//		}
		String sql = " insert into  t_bookmark (bookmark_id,user_id) values ('1234567891234567891','110')" ;
		int rs = stmt.executeUpdate(sql);
		  
		System.out.println(rs);
//		rs.close();
		stmt.close();
		con.close();
		//System.out.println("1");
		
		
//		System.out.println("yes");
	}

}
