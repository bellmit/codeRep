/**
 * user info center(uic)
 * tyread.com Inc.
 * Copyright (c) �����Ķ��Ļ��������޹�˾  All Rights Reserved.
 */
package util.OOMtest;

/**
 * @author <a href="mailto:zhuhao.567@163.com.">���</a>
 * 2014��12��16������9:48:39
 * @version 1.0
 */
public class TestGC {
    private String largeString = new String(new byte[100000]);
 
    String getString() {
//        return this.largeString.substring(0,2); //������ڴ�й¶
    	return new String("ab");    //��������ڴ�й¶
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
