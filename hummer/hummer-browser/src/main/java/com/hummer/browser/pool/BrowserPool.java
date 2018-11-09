package com.hummer.browser.pool;

import com.hummer.browser.Browser;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.DefaultPooledObjectInfo;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.IOException;
import java.util.Set;

/**
 * @Discribe
 * @Author gzy
 * @Date 2018/10/23 17:37
 */
public class BrowserPool extends AbstractBrowserPool<Browser> {


    public BrowserPool() {
    }

    public BrowserPool(GenericObjectPoolConfig poolConfig, BrowserPoolFactory factory) {
        super(poolConfig, factory);
    }

    public BrowserPool(GenericObjectPoolConfig poolConfig, BrowserPoolFactory factory, AbandonedConfig abandonedConfig) {
        super(poolConfig,factory,abandonedConfig);
    }

}
