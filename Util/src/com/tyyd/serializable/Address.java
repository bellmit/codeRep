package com.tyyd.serializable;

import java.io.Serializable;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��7��24������2:37:27
 * @version 1.0
 */
public class Address implements Serializable{
	 
	   private static final long serialVersionUID = 1L;

	   String street;
	   String country;

	   public void setStreet(String street){
		   this.street = street;
	   }

	   public void setCountry(String country){
		   this.country = country;
	   }

	   public String getStreet(){
		   return this.street;
	   }

	   public String getCountry(){
		   return this.country;
	   }

	   @Override
	   public String toString() {
 	   return new StringBuffer(" Street : ")
 	   .append(this.street)
 	   .append(" Country : ")
 	   .append(this.country).toString();
	   }
}
