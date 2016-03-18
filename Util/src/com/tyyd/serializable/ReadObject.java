package com.tyyd.serializable;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��7��24������2:38:41
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
