package com.java.base.filepath;

import java.io.File;
import java.nio.Buffer;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��8��5������9:37:45
 * @version 1.0
 */
public class filepath {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filePath = System.getProperty("user.home") + "/diamond";
		System.out.println(filePath);	
		File dir = new File(filePath);
	    dir.mkdirs();
	}

}
