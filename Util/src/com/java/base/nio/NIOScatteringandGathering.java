package com.java.base.nio;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年8月14日下午3:24:38
 * @version 1.0
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOScatteringandGathering {
	public void createFiles(String TPATH) {
		try {
			ByteBuffer bookBuf = ByteBuffer.wrap("java 性能优化技巧"
					.getBytes("utf-8"));
			ByteBuffer autBuf = ByteBuffer.wrap("test".getBytes("utf-8"));
			int booklen = bookBuf.limit();
			int autlen = autBuf.limit();
			ByteBuffer[] bufs = new ByteBuffer[] { bookBuf, autBuf };
			File file = new File(TPATH);
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				FileOutputStream fos = new FileOutputStream(file);
				FileChannel fc = fos.getChannel();
				fc.write(bufs);
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			ByteBuffer b1 = ByteBuffer.allocate(booklen);
			ByteBuffer b2 = ByteBuffer.allocate(autlen);
			ByteBuffer[] bufs1 = new ByteBuffer[] { b1, b2 };
			File file1 = new File(TPATH);
			try {
				FileInputStream fis = new FileInputStream(file);
				FileChannel fc = fis.getChannel();
				fc.read(bufs1);
				String bookname = new String(bufs1[0].array(), "utf-8");
				String autname = new String(bufs1[1].array(), "utf-8");
				System.out.println(bookname + " " + autname);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		NIOScatteringandGathering nio = new NIOScatteringandGathering();
		nio.createFiles("C:\\1.TXT");
	}
}
