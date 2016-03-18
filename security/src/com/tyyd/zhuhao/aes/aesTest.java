package com.tyyd.zhuhao.aes;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��9��21������10:17:31
 * @version 1.0
 */
public class aesTest {
	private static String src = "imooc security aes";
	public static void main(String[] args) {
		jdkAES();
	}

	private static void jdkAES() {
		try {
			//����KEY
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128);
			SecretKey secretKey = keyGenerator.generateKey();
			byte[] keyBytes = secretKey.getEncoded();
			
			//KEYת��
			Key key = new SecretKeySpec(keyBytes, "AES");
			
			//����
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] result = cipher.doFinal(src.getBytes());
			System.out.println("jdk aes encrypt :" + Base64.encodeBase64String(result));
			
			//����
			cipher.init(Cipher.DECRYPT_MODE, key);
			result = cipher.doFinal(result);
			System.out.println("jdk aes decrypt :" + new String(result));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private static void bcAES() {
		//TODO
	}
}