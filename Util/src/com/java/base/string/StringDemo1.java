package com.java.base.string;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年8月18日下午2:35:23
 * @version 1.0
 * 下面代码演示了使用 substring 方法在一个很大的 string 独享里面截取一段很小的字符串，
 * 如果采用 string 的 substring 方法会造成内存溢出，如果采用反复创建新的 string 方法可以确保正常运行。
 */
import java.util.ArrayList;
import java.util.List;

public class StringDemo1 {
	public static void main(String[] args) {
		List<String> handler = new ArrayList<String>();
		for (int i = 0; i < 1000; i++) {
			HugeStr h = new HugeStr();
			ImprovedHugeStr h1 = new ImprovedHugeStr();
			handler.add(h.getSubString(1, 5));
			handler.add(h1.getSubString(1, 5));
		}
	}

	static class HugeStr {
		private String str = new String(new char[800000]);

		public String getSubString(int begin, int end) {
			return str.substring(begin, end);
		}
	}

	static class ImprovedHugeStr {
		private String str = new String(new char[10000000]);

		public String getSubString(int begin, int end) {
			return new String(str.substring(begin, end));
		}
	}
}
