package com.dengliang.myspringmvc.framework.dao;

public class DBException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1550744875526476827L;
	public DBException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DBException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public DBException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public DBException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DBException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}