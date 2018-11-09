package com.hummer.browser.pool;

import com.hummer.browser.Browser;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.*;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * @Discribe
 * @Author gzy
 * @Date 2018/10/19 9:47
 */
public abstract class AbstractBrowserPool<T> implements Closeable{

    //定义浏览器失效时间（浏览器未操作的时间）
    private final static long SESSION_OUTTIME = 60*1000;

    protected GenericObjectPool<T> internalPool;

    /**
     * 浏览器监视器
     * @param internalPool
     * @throws Exception
     */
    private static void monitorBrowser(GenericObjectPool<Browser> internalPool) throws Exception {
        //通过反射获取目标实例的属性和方法
        Class<GenericObjectPool> reflectPool = GenericObjectPool.class;
        GenericObjectPool<Browser> genericObjectPool = internalPool;
        //获取目标实体的属性
        Field allObjects = reflectPool.getDeclaredField("allObjects");
        Method destroyMethod = reflectPool.getDeclaredMethod("destroy",PooledObject.class);
        //修改私有属性、方法的执行权限
        allObjects.setAccessible(true);
        destroyMethod.setAccessible(true);

        Map<Object, PooledObject<Browser>> pooledObjectMap = (Map<Object, PooledObject<Browser>>)allObjects.get(genericObjectPool);

        Collection<PooledObject<Browser>> pooledObjects = pooledObjectMap.values();
        System.out.println("pool.size: "+pooledObjects.size());
        pooledObjects.forEach(poolObject ->{
            //判断当浏览器被1分钟之内没有操作，即视为假死，即销毁pool中实例，并关闭该进程
            boolean flag = internalPool.getFactory().validateObject(poolObject);
            if (!flag){
                try {
                    destroyMethod.invoke(internalPool,poolObject);
                    //System.out.println("Destoried Browser is "+poolObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public AbstractBrowserPool() {
    }

    public AbstractBrowserPool(final GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory, AbandonedConfig abandonedConfig) {
        this.initPool(poolConfig,factory,abandonedConfig);
    }

    public AbstractBrowserPool(final GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory) {
        this.initPool(poolConfig,factory,null);
    }
    //初始化浏览器池
    public void initPool(final GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory,AbandonedConfig abandonedConfig) {

        if (this.internalPool != null) {
            try {
                closeInternalPool();
            } catch (Exception e) {
            }
        }
        if (abandonedConfig != null){
            this.internalPool = new GenericObjectPool<T>(factory, poolConfig,abandonedConfig);
        }else {
            this.internalPool = new GenericObjectPool<T>(factory, poolConfig);
        }
            //单独运行线程，监视浏览器是否存活
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true){
                            Thread.sleep(SESSION_OUTTIME);
                            System.out.println("监听开始......");
                            monitorBrowser((GenericObjectPool<Browser>) internalPool);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();


    }
    @Override
    public void close() throws IOException {
        destroy();
    }

    //关闭连接池
    protected void closeInternalPool() {
        try {
            internalPool.clear();
            internalPool.close();
        } catch (Exception e) {
            throw new BrowserException("Could not destroy the pool", e);
        }
    }

    /**
     * 判断连接池是否关闭
     * @return
     */
    public boolean isClosed() {
        return this.internalPool.isClosed();
    }

    /**
     * 返回一个浏览器
     * @return
     */
    public T getBrowser(){
        try {
            return internalPool.borrowObject();
        }catch (NoSuchElementException nse) {
            if (null == nse.getCause()) {
                throw new BrowserException("Could not get a browser since the pool is exhausted",nse);
            }
            throw new BrowserException("Could not get a browser from the pool", nse);
        }catch (Exception e){
            throw new BrowserException("Could not get a resource from the pool",e);
        }
    }


    protected void returnBrowserObject(final T browser){
        if (browser == null ){
            return;
        }
        try {
            internalPool.returnObject(browser);
        }catch (Exception e){
            throw new BrowserException("Could not return browser to the pool",e);
        }

    }
    /**
     * 归还浏览器对象
     * @param browser
     */
    public void returnBrowser(final T browser) {
        if (browser != null) {
            returnBrowserObject(browser);
        }
    }

    /**
     * 归还问题浏览器
     * @param browser
     */
    public void returnBrokenBrowser(final T browser){
        if (browser != null){
            returnBrokenBrowserObject(browser);
        }
    }

    protected void returnBrokenBrowserObject(final T browser){
        try {
            internalPool.invalidateObject(browser);
        } catch (Exception e) {
            throw new BrowserException("Cloud not return the broken browser to the pool",e);
        }
    }

    /**
     * 关闭连接池
     */
    public void destroy() {
        closeInternalPool();
    }

    /**
     * 判断连接池是否关闭或者为null
     * @return
     */
    private boolean poolInactive() {
        return this.internalPool == null || this.internalPool.isClosed();
    }

    public int getNumActive() {
        if (poolInactive()) {
            return -1;
        }
        return this.internalPool.getNumActive();
    }


    public int getNumIdle() {
        if (poolInactive()) {
            return -1;
        }
        return this.internalPool.getNumIdle();
    }

    public int getNumWaiters() {
        if (poolInactive()) {
            return -1;
        }

        return this.internalPool.getNumWaiters();
    }

    public long getMeanBorrowWaitTimeMillis() {
        if (poolInactive()) {
            return -1;
        }

        return this.internalPool.getMeanBorrowWaitTimeMillis();
    }

    public long getMaxBorrowWaitTimeMillis() {
        if (poolInactive()) {
            return -1;
        }

        return this.internalPool.getMaxBorrowWaitTimeMillis();
    }

    public void addObjects(int count) {
        try {
            for (int i = 0; i < count; i++) {
                this.internalPool.addObject();
            }
        } catch (Exception e) {
            throw new BrowserException("Error trying to add idle objects", e);
        }
    }

}
