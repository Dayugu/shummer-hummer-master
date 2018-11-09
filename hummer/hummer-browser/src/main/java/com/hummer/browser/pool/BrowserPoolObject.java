package com.hummer.browser.pool;

import com.hummer.browser.Browser;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * @Discribe
 * @Author gzy
 * @Date 2018/10/22 17:45
 */
public class BrowserPoolObject extends DefaultPooledObject<Browser> {


    public BrowserPoolObject(Browser object) {
        super(object);
    }
}
