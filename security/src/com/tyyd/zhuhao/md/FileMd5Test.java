package com.tyyd.zhuhao.md;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;


/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年9月21日下午5:03:18
 * @version 1.0
 */
public class FileMd5Test {
	public static void main(String[] args) {
		try {
			String path = "E:\\operation-client-1.6.1.jar";
			FileInputStream fis = new FileInputStream(path);
			String md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
			IOUtils.closeQuietly(fis);
			System.out.println("MD5:" + md5);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
