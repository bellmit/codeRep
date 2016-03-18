package com.tyyd.zhuhao.hmac;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年9月17日下午12:55:04
 * @version 1.0
 */
public class hmacTest {
	private static String src = "imooc security hmac";
	public static void main(String[] args) {
		jdkHmacMD5();
		bcHmacMD5();
	}
	
	public static void jdkHmacMD5() {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");//初始化KeyGenerator
			SecretKey secretKey = keyGenerator.generateKey();//产生秘钥
			byte[] key = secretKey.getEncoded();//获得秘钥
			
			SecretKey restoreSecretKey = new SecretKeySpec(key, "HmacMD5");//还原秘钥
			Mac mac = Mac.getInstance(restoreSecretKey.getAlgorithm());//实例化MAC
			mac.init(restoreSecretKey);//初始化MAC
			byte[] hmacMD5Bytes = mac.doFinal(src.getBytes());//执行摘要
			System.out.println("jdk hmacMD5 :" + Hex.encodeHexString(hmacMD5Bytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void bcHmacMD5() {
		HMac hMac = new HMac(new MD5Digest());
		hMac.init(new KeyParameter(org.bouncycastle.util.encoders.Hex.decode("aaaaaaaaaa")));
		hMac.update(src.getBytes(),0,src.getBytes().length);
		byte[] hmacMD5Bytes = new byte[hMac.getMacSize()];
		hMac.doFinal(hmacMD5Bytes, 0);
		System.out.println("bc hmacMD5 :" + Hex.encodeHexString(hmacMD5Bytes));
	}
}
