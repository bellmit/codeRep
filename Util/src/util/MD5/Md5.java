/**
 * user info center(uic)
 * tyread.com Inc.
 * Copyright (c) 天翼阅读文化传播有限公司  All Rights Reserved.
 */
package util.MD5;

import java.security.MessageDigest;

/**
 * @author <a href="mailto:zhuhao.567@163.com.">朱豪</a>
 * 2014年11月12日下午3:11:59
 * @version 1.0
 */
public class Md5 {
	 public Md5()
	    {
	    }

	    public static String byteArrayToHexString(byte byteArray[])
	    {
	        StringBuffer strHex = new StringBuffer();
	        for(int i = 0; i < byteArray.length; i++)
	            strHex.append(byteToHexString(byteArray[i]));

	        return strHex.toString();
	    }

	    private static String byteToHexString(byte bt)
	    {
	        int i = bt;
	        if(i < 0) i += 256;
	        int j = i / 16;
	        int k = i % 16;
	        return hexDigits[j] + hexDigits[k];
	    }

	    public static String MD5Encode(String strOrigin)
	    {
	        String strAim = null;
	        try
	        {
	        	strAim = new String(strOrigin);
	            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
	            strAim = byteArrayToHexString(messageDigest.digest(strAim.getBytes()));
	        }
	        catch(Exception exception) { }
	        return strAim;
	    }

	    private static final String hexDigits[] = {
	        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", 
	        "a", "b", "c", "d", "e", "f"
	    };
	    
	    public static void main(String[] args) {
			String strOrigin = "testappkeytestappsecret1415250311646";
			String s = Md5.MD5Encode(strOrigin);
			System.out.println(s);
		}
}
