/**
 * tyread.com Inc.
 * Copyright (c) 天翼阅读文化传播有限公司  All Rights Reserved.
 */
package com.java.base.json;

import net.sf.json.JSONObject;

/**
 * @author <a href="mailto:zhuhao.567@163.com.">朱豪</a>
 * 2015年1月14日下午2:10:55
 * @version 1.0
 */
public class JsonTest {
	public static void main(String[] args) {
		try {
//			String jsonArray = "{'target_count':'1','target_desc':'desc-0.01元QB,type-QB,markedPrice-1,actualPrice-1,number-'}";
//			String jsonArray = "{'result':'{'errNo': '0','msg': '234','msg2':'{'a': 1,'b': 2,'c': 3}'}'}";
			String jsonArray = "{'1':'13','2':'12','3':'11'}";
			int i = jsonArray.split(",").length;
			System.out.println(i);

			JSONObject jo = JSONObject.fromObject(jsonArray); 
			System.out.println(jo.size());
//			jo.put("target_desc", "test");
//			System.out.println(jo.get("result"));
//			JSONObject jsonObject =  JSONObject.fromObject(jo.get("result").toString().substring(1).substring(0, 9));
//			System.out.println(jsonObject.toString());
//			System.out.println(jsonObject.get("a"));
//			System.out.println(jo.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("not json");
			e.printStackTrace();
		}
	}
}
