package com.learn.spring.framework.util;

public class MockBeanException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1838472161180011883L;

	public MockBeanException() {
		super();
	}

	public MockBeanException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MockBeanException(String message, Throwable cause) {
		super(message, cause);
	}

	public MockBeanException(String message) {
		super(message);
	}

	public MockBeanException(Throwable cause) {
		super(cause);
	}

}
