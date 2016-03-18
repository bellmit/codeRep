package com.java.base.buffer;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WriterVSBuffer {
	public static void streamMethod() throws IOException {
		try {
			long start = System.currentTimeMillis();
			FileWriter fw = new FileWriter("C://StreamVSBuffertest.txt");// 请替换成自己的文件
			for (int i = 0; i < 10000; i++) {
				fw.write(String.valueOf(i) + "/r/n");// 循环 1 万次写入数据
			}
			fw.close();
			FileReader fr = new FileReader("C://StreamVSBuffertest.txt");
			while (fr.ready() != false) {
			}
			fr.close();
			System.out.println(System.currentTimeMillis() - start);
		} catch (FileNotFoundException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void bufferMethod() throws IOException {
		try {
			long start = System.currentTimeMillis();
			BufferedWriter fw = new BufferedWriter(new FileWriter(
					"C://StreamVSBuffertest.txt"));// 请替换成自己的文件
			for (int i = 0; i < 10000; i++) {
				fw.write(String.valueOf(i) + "/r/n");// 循环 1 万次写入数据
			}
			fw.close();
			BufferedReader fr = new BufferedReader(new FileReader(
					"C://StreamVSBuffertest.txt"));
			while (fr.ready() != false) {
			}
			fr.close();
			System.out.println(System.currentTimeMillis() - start);
		} catch (FileNotFoundException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			StreamVSBuffer.streamMethod();
			StreamVSBuffer.bufferMethod();
		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
