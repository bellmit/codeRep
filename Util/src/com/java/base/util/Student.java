package com.java.base.util;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年8月12日下午3:05:38
 * @version 1.0
 */
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Student implements Comparable<Student> {

	public String name;
	public int score;

	public Student(String name, int score) {
		this.name = name;
		this.score = score;
	}

	@Override
	// 告诉 TreeMap 如何排序
	public int compareTo(Student o) {
		// TODO Auto-generated method stub
		if (o.score < this.score) {
			return 1;
		} else if (o.score > this.score) {
			return -1;
		}
		return 0;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("name:");
		sb.append(name);
		sb.append(" ");
		sb.append("score:");
		sb.append(score);
		return sb.toString();
	}

	public static void main(String[] args) {
		TreeMap map = new TreeMap();
		Student s1 = new Student("1", 100);
		Student s2 = new Student("2", 99);
		Student s3 = new Student("3", 97);
		Student s4 = new Student("4", 91);
		map.put(s1, new StudentDetailInfo(s1));
		map.put(s2, new StudentDetailInfo(s2));
		map.put(s3, new StudentDetailInfo(s3));
		map.put(s4, new StudentDetailInfo(s4));

		// 打印分数位于 S4 和 S2 之间的人
		Map map1 = ((TreeMap) map).subMap(s4, s2);
		for (Iterator iterator = map1.keySet().iterator(); iterator.hasNext();) {
			Student key = (Student) iterator.next();
			System.out.println(key + "->" + map.get(key));
		}
		System.out.println("subMap end");

		// 打印分数比 s1 低的人
		map1 = ((TreeMap) map).headMap(s1);
		for (Iterator iterator = map1.keySet().iterator(); iterator.hasNext();) {
			Student key = (Student) iterator.next();
			System.out.println(key + "->" + map.get(key));
		}
		System.out.println("subMap end");

		// 打印分数比 s1 高的人
		map1 = ((TreeMap) map).tailMap(s1);
		for (Iterator iterator = map1.keySet().iterator(); iterator.hasNext();) {
			Student key = (Student) iterator.next();
			System.out.println(key + "->" + map.get(key));
		}
		System.out.println("subMap end");
	}

}

class StudentDetailInfo {
	Student s;

	public StudentDetailInfo(Student s) {
		this.s = s;
	}

	@Override
	public String toString() {
		return s.name + "'s detail information";
	}
}
