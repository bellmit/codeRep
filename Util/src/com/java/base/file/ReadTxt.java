package com.java.base.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class ReadTxt {
	public static void main(String arg[]) {
		try {
			String encoding = "UTF-8"; // �ַ����(�ɽ�������������� )
			File file = new File("D:/jf.txt");
			File writeFile = new File("D:\\jfresu.txt");
			//String reg = "[|][1][\\d]{10}[|][|]";
			//Pattern pattern = Pattern.compile(reg);
			//Matcher matcher;
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(writeFile));
				String lineTXT = null;
				while ((lineTXT = bufferedReader.readLine()) != null) {
//					matcher = pattern.matcher(lineTXT);
//					while(matcher.find()){
						//String mString = matcher.group();
						String mString = lineTXT.trim();
						System.out.println(mString);
						bufferedWriter.write(mString);
						bufferedWriter.write("\r\n");
					//}
					//System.out.println(lineTXT.toString().trim());
				}
				bufferedWriter.flush();
				bufferedWriter.close();
				read.close();
			} else {
				System.out.println("�Ҳ���ָ�����ļ���");
			}
		} catch (Exception e) {
			System.out.println("��ȡ�ļ����ݲ�������");
			e.printStackTrace();
		}
	}
	
	public void read() {
		try {
			String encoding = "UTF-8"; // �ַ����(�ɽ�������������� )
			File file = new File("D:/jf.txt");
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTXT = null;
				while ((lineTXT = bufferedReader.readLine()) != null) {
						String mString = lineTXT.trim();
						write(mString);
				}
				read.close();
			} else {
				System.out.println("�Ҳ���ָ�����ļ���");
			}
		} catch (Exception e) {
			System.out.println("��ȡ�ļ����ݲ�������");
			e.printStackTrace();
		}
	}
	
	public void write(String s) {
		try {
			File writeFile = new File("D:\\jfresu.txt");
			if (writeFile.isFile() && writeFile.exists()) {
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(writeFile));
				bufferedWriter.write(s);
				bufferedWriter.write("\r\n");
				bufferedWriter.flush();
				bufferedWriter.close();
			} else {
				System.out.println("�Ҳ���ָ�����ļ���");
			}
		} catch (Exception e) {
			System.out.println("��ȡ�ļ����ݲ�������");
			e.printStackTrace();
		}
	}
}
