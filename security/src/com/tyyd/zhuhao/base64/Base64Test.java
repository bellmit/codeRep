package com.tyyd.zhuhao.base64;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年9月17日上午9:42:44
 * @version 1.0
 */
public class Base64Test {
	private static String src = "imooc security base64";
	
	public static void main(String[] args) {
//		jdkBase64();
//		commonsCodecBase64();
		bouncyCastleBase64();
	}
	
	public static void jdkBase64() {
		try {
			BASE64Encoder encoder = new BASE64Encoder();
			String encode = encoder.encode(src.getBytes());
			System.out.println("encode :" + encode);

			BASE64Decoder decoder = new BASE64Decoder();
			byte[] decoderBytes = decoder.decodeBuffer(encode);
			System.out.println("decode :" + new String(decoderBytes));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void commonsCodecBase64() {
		byte[] encodeBytes = Base64.encodeBase64(src.getBytes());
		System.out.println("encode :" + new String(encodeBytes));
		
		byte[] decodeBytes = Base64.decodeBase64(encodeBytes);
		System.out.println("decode :" + new String(decodeBytes));
	}
	
	public static void bouncyCastleBase64() {
		byte[] encodeBytes = org.bouncycastle.util.encoders.Base64.encode(src.getBytes());
		System.out.println("encode :" + new String(encodeBytes));
		
		byte[] decodeBytes= org.bouncycastle.util.encoders.Base64.decode(encodeBytes);
		System.out.println("decode :" + new String(decodeBytes));
		
	}
}
