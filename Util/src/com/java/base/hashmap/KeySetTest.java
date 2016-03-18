package com.java.base.hashmap;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年4月9日下午3:33:44
 * @version 1.0
 */
public class KeySetTest {
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("a", "1");
		map.put("b", "2");
		map.put("c", "3");
		map.put("d", "4");
		
		System.out.print("HashMap:");
		for(String key : map.keySet()) {
			System.out.print(map.get(key) + " ");
		}
		
		Map<String, String> linkedMap = new LinkedHashMap<String, String>();
		linkedMap.put("a", "1");
		linkedMap.put("b", "2");
		linkedMap.put("c", "3");
		linkedMap.put("d", "4");
		
		System.out.print("LinkedHashMap:");
		for(String key : linkedMap.keySet()) {
			System.out.print(linkedMap.get(key) + " ");
		}
		
		Map<String, String> treeMap = new TreeMap<String, String>();
		treeMap.put("a", "1");
		treeMap.put("b", "2");
		treeMap.put("c", "3");
		treeMap.put("d", "4");
		
		System.out.print("TreeMap:");
		for(String key : treeMap.keySet()) {
			System.out.print(treeMap.get(key) + " ");
		}
	}
}
