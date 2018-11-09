package com.hummer.browser.pool;

/**
 * @Discribe
 * @Author gzy
 * @Date 2018/10/23 16:20
 */
public class BrowserException extends RuntimeException {

    public BrowserException(String message) {
        super(message);
    }

    public BrowserException(String message, Throwable cause) {
        super(message, cause);
    }

    public BrowserException(Throwable cause) {
        super(cause);
    }

    public BrowserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
