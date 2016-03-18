/**
 * user info center(uic)
 * tyread.com Inc.
 * Copyright (c) �����Ķ��Ļ��������޹�˾  All Rights Reserved.
 */
package com.tyyd.fkfb;

/**
 * @author <a href="mailto:zhuhao.567@163.com.">���</a>
 * 2014��11��24������10:16:59
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
* ������ǩ����ķֿ�ֱ�		
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
		System.out.println("-----------���������ķֿ�ֱ�--------------");
		System.out.println("���ã�" + DB + " " + tableName);
		System.out.println("�̲⣺" + DBSC + " " + tableNameSC);
		
		
/**
 * ������ǩ����ķֿ�ֱ�		
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
		System.out.println("-------------ǩ����ķֿ�ֱ�---------------");
		System.out.println("SI���ã�" + SIDB + " " + SItableName);
		System.out.println("SI�̲⣺" + SIDBSC + " " + SItableNameSC);
	}
}
