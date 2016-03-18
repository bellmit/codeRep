package com.tyyd.serializable;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��7��24������2:38:05
 * @version 1.0
 */
public class WriteObject {
	public static void main (String args[]) {
		 
		   Address address = new Address();
		   address.setStreet("wall street");
		   address.setCountry("united states");
	 
		   try{
	 
			FileOutputStream fout = new FileOutputStream("d:\\address.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fout);   
			oos.writeObject(address);
			oos.close();
			System.out.println("Done");
	 
		   }catch(Exception ex){
			   ex.printStackTrace();
		   } 
		}
}
