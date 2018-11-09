package com.hummer.browser.exception;

public class BrowserExhaustedPoolException extends BrowserException {

	private static final long serialVersionUID = 2625569803661483605L;

	public BrowserExhaustedPoolException(String message) {
	    super(message);
	  }

	public BrowserExhaustedPoolException(Throwable e) {
	    super(e);
	  }

	public BrowserExhaustedPoolException(String message, Throwable cause) {
	    super(message, cause);
	  }

}
