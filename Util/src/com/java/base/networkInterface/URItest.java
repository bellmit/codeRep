package com.java.base.networkInterface;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年8月6日上午10:33:47
 * @version 1.0
 */
public class URItest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			URI uri = new URI("http://java.sun.com/");
			URL url = uri.toURL();
			InputStream in = url.openStream();
			System.out.println(in.toString());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
