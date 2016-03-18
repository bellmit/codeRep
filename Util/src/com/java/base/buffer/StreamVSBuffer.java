package com.java.base.buffer;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class StreamVSBuffer {
	public static void streamMethod() throws IOException {
		try {
			long start = System.currentTimeMillis(); // ���滻���Լ����ļ�
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(
					"C://StreamVSBuffertest.txt"));
			for (int i = 0; i < 10000; i++) {
				dos.writeBytes(String.valueOf(i) + "/r/n");// ѭ�� 1 ���д������
			}
			dos.close();
			DataInputStream dis = new DataInputStream(new FileInputStream(
					"C://StreamVSBuffertest.txt"));
			while (dis.readLine() != null) {
			}
			dis.close();
			System.out.println(System.currentTimeMillis() - start);
		} catch (FileNotFoundException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void bufferMethod() throws IOException {
		try {
			long start = System.currentTimeMillis(); // ���滻���Լ����ļ�
			DataOutputStream dos = new DataOutputStream(
					new BufferedOutputStream(new FileOutputStream(
							"C://StreamVSBuffertest.txt")));
			for (int i = 0; i < 10000; i++) {
				dos.writeBytes(String.valueOf(i) + "/r/n");// ѭ�� 1 ���д������
			}
			dos.close();
			DataInputStream dis = new DataInputStream(new BufferedInputStream(
					new FileInputStream("C://StreamVSBuffertest.txt")));
			while (dis.readLine() != null) {
			}
			dis.close();
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
