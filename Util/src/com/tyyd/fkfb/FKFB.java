/**
 * user info center(uic)
 * tyread.com Inc.
 * Copyright (c) 天翼阅读文化传播有限公司  All Rights Reserved.
 */
package com.tyyd.fkfb;

/**
 * @author <a href="mailto:zhuhao.567@163.com.">朱豪</a>
 * 2014年11月24日上午10:16:59
 * @version 1.0
 */
public class FKFB {
	public int getTableIndex(Long userId){
		long ret = 0;
		if(null != userId){
			ret = userId%1024;
		}
		return (int)ret;
	}
	
	public int getTableIndexSC(Long userId){
		long ret = 0;
		if(null != userId){
			ret = userId%32;
		}
		return (int)ret;
	}
	
	public static void main(String args[]) {
		FKFB f = new FKFB();
		Long userId = 210000085336829L;
//		Long userId = 10010446865214L;
//		Long userId = 210000184446262L;
		
/**
* 以下是签到表的分库分表		
 */
		int SY = 32;
		int SC = 16;
		String baseTableName = "t_user_point_log_";
		String baseDBName = "user_point_log_";

		int tableIndex = f.getTableIndex(userId);
		int tableIndexSC = f.getTableIndexSC(userId);
		String DB = baseDBName + tableIndex/SY;
		String DBSC = baseDBName + tableIndexSC/SC;
		String tableName = baseTableName + tableIndex;
		String tableNameSC = baseTableName + tableIndexSC;
		System.out.println("-----------积分详情表的分库分表--------------");
		System.out.println("商用：" + DB + " " + tableName);
		System.out.println("商测：" + DBSC + " " + tableNameSC);
		
		
/**
 * 以下是签到表的分库分表		
 */
		int SISY = 32;
		int SISC = 32;
		String SIbaseTableName = "sign_in_log_";
		String SIbaseDBName = "sign_in_";
		
		int SItableIndex = f.getTableIndex(userId);
		int SItableIndexSC = f.getTableIndexSC(userId);
		String SIDB = SIbaseDBName + SItableIndex/SISY;
		String SIDBSC = SIbaseDBName + SItableIndexSC/SISC;
		String SItableName = SIbaseTableName + SItableIndex;
		String SItableNameSC = SIbaseTableName + SItableIndexSC;
		System.out.println("-------------签到表的分库分表---------------");
		System.out.println("SI商用：" + SIDB + " " + SItableName);
		System.out.println("SI商测：" + SIDBSC + " " + SItableNameSC);
	}
}
