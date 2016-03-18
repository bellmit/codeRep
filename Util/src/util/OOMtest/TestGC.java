/**
 * user info center(uic)
 * tyread.com Inc.
 * Copyright (c) 天翼阅读文化传播有限公司  All Rights Reserved.
 */
package util.OOMtest;

/**
 * @author <a href="mailto:zhuhao.567@163.com.">朱豪</a>
 * 2014年12月16日上午9:48:39
 * @version 1.0
 */
public class TestGC {
    private String largeString = new String(new byte[100000]);
 
    String getString() {
//        return this.largeString.substring(0,2); //会造成内存泄露
    	return new String("ab");    //不会造成内存泄露
    }
 
    public static void main(String[] args) {
    	Long begin = System.currentTimeMillis();
        java.util.ArrayList list = new java.util.ArrayList();
        for (int i = 0; i < 1000000; i++) {
            TestGC gc = new TestGC();
            list.add(gc.getString());
        }
        System.out.println("time:" + (System.currentTimeMillis()-begin) + "ms");
    }
}
