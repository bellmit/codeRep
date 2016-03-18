package com.tyyd.serializable;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年7月24日下午2:38:41
 * @version 1.0
 */
public class ReadObject {
	public static void main (String args[]) {
		 
		   Address address;
	 
		   try{
	 
			   FileInputStream fin = new FileInputStream("d:\\address.ser");
			   ObjectInputStream ois = new ObjectInputStream(fin);
			   address = (Address) ois.readObject();
			   ois.close();
	 
			   System.out.println(address);
	 
		   }catch(Exception ex){
			   ex.printStackTrace(); 
		   } 
	   }
}
