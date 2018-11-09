package com.hummer.browser.pool;

import com.hummer.browser.Browser;
import com.hummer.browser.BrowserContext;
import com.hummer.browser.phantomjs.PhantomJSBrowser;
import com.hummer.browser.phantomjs.PhantomJsBrowserConfig;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.openqa.selenium.Platform;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;

import java.text.SimpleDateFormat;

/**
 * @Discribe
 * @Author gzy
 * @Date 2018/10/22 10:07
 */
public class BrowserPoolFactory implements PooledObjectFactory<Browser> {

     final static long DEFAULT_OUT_TIME = 60;//浏览器默认最大空闲时间

    /**
     * 创造浏览器
     * @return
     * @throws Exception
     */
    @Override
    public PooledObject<Browser> makeObject() throws Exception {
        System.out.println("[makeObject]");
        PhantomJsBrowserConfig config = new PhantomJsBrowserConfig();

        config.setOs(Platform.getCurrent());
        if (config.getOs().equals("Linux")){
            config.setExecuteFile("/opt/spider/phantomjs-2.1.1-linux-x86_64/bin/phantomjs");
            //dc.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"");
        }else {
            config.setExecuteFile("G:\\归档\\shummer-hummer-master\\hummer\\hummer-browser\\phantomjs.exe");
            //dc.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"G:\\归档\\shummer-hummer-master\\hummer\\hummer-browser\\phantomjs.exe");
        }
        PhantomJSBrowser phantomJSBrowser = new PhantomJSBrowser(config);

        return new DefaultPooledObject<Browser>(phantomJSBrowser);
    }

    /**
     * 销毁浏览器
     * @param pooledObject
     * @throws Exception
     */
    @Override
    public void destroyObject(PooledObject<Browser> pooledObject) throws Exception {
        System.out.println("[destroyObject]");

        Browser object = pooledObject.getObject();
        object.destory();

        object = null;

    }

    /**
     * 验证浏览器是否存活
     * @param pooledObject
     * @return
     */
    @Override
    public boolean validateObject(PooledObject<Browser> pooledObject) {
        Browser browser = pooledObject.getObject();
        BrowserContext browserContext = browser.getContext();
        //获取当前被借出浏览器的空闲时间
        long freeTime = (System.currentTimeMillis() - browserContext.getLastTime()) / 1000;
        boolean flag = false;
        //判断当前浏览器如果在60秒内没有被操作，即返回false
        if(freeTime < DEFAULT_OUT_TIME){
            flag = true;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        System.out.println("[validateObject] validate result: "+flag+" ,lastUsedTime"+simpleDateFormat.format(browserContext.getLastTime())+" , freeTime:"+freeTime);
        return flag;

    }

    /**
     * 激活浏览器,暂时不做处理
     * @param pooledObject
     * @throws Exception
     */
    @Override
    public void activateObject(PooledObject<Browser> pooledObject) throws Exception {
        System.out.println("[activateObject] ");

    }

    /**
     * 钝化处理(卸载)，暂时不做处理
     * @param pooledObject
     * @throws Exception
     */
    @Override
    public void passivateObject(PooledObject<Browser> pooledObject) throws Exception {
        System.out.println("[passivateObject]");
        Browser browser = pooledObject.getObject();
        BrowserPoolObject poolObject = new BrowserPoolObject(browser);
        //清空cookie和session
        browser.reset();

        //System.out.println(browser.toString()+" 最后的活动状态中使用的时间（getActiveTimeMillis）:"+poolObject.getActiveTimeMillis()+", 上次借用时间："+poolObject.getLastBorrowTime()+",上次使用时间的一个估计值："+poolObject.getLastUsedTime());

    }
}
