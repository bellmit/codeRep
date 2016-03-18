package com.tyyd.zhuhao.md;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年9月17日上午10:33:36
 * @version 1.0
 */
public class MDTest {
	private static String src = "zhuhao617";
	
	public static void main(String[] args) {
		jdkMD5();
		jdkMD2();
		bcMD4();
		bcMD5();
		ccMD5();
		ccMD2();
	}
	
	public static void jdkMD5() {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] md5Bytes = md.digest(src.getBytes());
			System.out.println("JDK MD5:" + Hex.encodeHexString(md5Bytes));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public static void jdkMD2() {
		try {
			MessageDigest md = MessageDigest.getInstance("MD2");
			byte[] md5Bytes = md.digest(src.getBytes());
			System.out.println("JDK MD2:" + Hex.encodeHexString(md5Bytes));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public static void bcMD5() {
		Digest digest = new MD5Digest();
		digest.update(src.getBytes(), 0, src.getBytes().length);
		byte[] md5Bytes = new byte[digest.getDigestSize()];
		digest.doFinal(md5Bytes, 0);
		System.out.println("BC MD5:" + org.bouncycastle.util.encoders.Hex.toHexString(md5Bytes));
	}
	
	public static void bcMD4() {
		try {
			Security.addProvider(new BouncyCastleProvider());
			MessageDigest md = MessageDigest.getInstance("MD4");
			byte[] md4Bytes = md.digest(src.getBytes());
			System.out.println("BC MD4:" + Hex.encodeHexString(md4Bytes));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		
		
//		Digest digest = new MD4Digest();
//		digest.update(src.getBytes(), 0, src.getBytes().length);
//		byte[] md4Bytes = new byte[digest.getDigestSize()];
//		digest.doFinal(md4Bytes, 0);
//		System.out.println("BC MD4:" + org.bouncycastle.util.encoders.Hex.toHexString(md4Bytes));
	}
	
	public static void ccMD5() {
		String ccMD5 = DigestUtils.md5Hex(src.getBytes());
		System.out.println("CC MD5:" + ccMD5);
	}
	
	public static void ccMD2() {
		String ccMD2 = DigestUtils.md2Hex(src.getBytes());
		System.out.println("CC MD2:" + ccMD2);
	}
}
