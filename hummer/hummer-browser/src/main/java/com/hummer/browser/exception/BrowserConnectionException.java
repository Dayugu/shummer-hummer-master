package com.hummer.browser.exception;

public class BrowserConnectionException extends BrowserException {

	private static final long serialVersionUID = -8816774634571505141L;

	public BrowserConnectionException(String message) {
	    super(message);
	  }

	public BrowserConnectionException(Throwable e) {
	    super(e);
	  }

	public BrowserConnectionException(String message, Throwable cause) {
	    super(message, cause);
	  }

}
