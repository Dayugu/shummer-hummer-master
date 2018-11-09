package com.hummer.browser.exception;

public class BrowserException extends RuntimeException {

	private static final long serialVersionUID = 3716270743682339481L;

	public BrowserException(String message) {
	    super(message);
	  }

	public BrowserException(Throwable e) {
	    super(e);
	  }

	public BrowserException(String message, Throwable cause) {
	    super(message, cause);
	  }
}
