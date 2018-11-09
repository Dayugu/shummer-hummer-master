package com.hummer.browser.phantomjs;

import java.io.File;
import java.net.URL;
import java.util.Map;

import com.hummer.browser.BrowserContext;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.service.DriverService;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.hummer.browser.AbstractBrowser;
import com.hummer.browser.BrowserConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhantomJSBrowser extends AbstractBrowser {

    private static final Logger logger = LoggerFactory.getLogger(PhantomJSBrowser.class);
    private Object script;


    public PhantomJSBrowser(BrowserConfig<?> config) {
		super(config);
	}

	public static void main(String[] args) {
        System.setProperty("", "phantomjs");
        PhantomJSDriver driver = new PhantomJSDriver();
    }
	
	@Override
	protected DriverService createDefaultDriverService(File exe, int port, ImmutableList<String> args,
			ImmutableMap<String, String> env) {
        logger.info("[PhantomjsBrowser.createDefaultDriverService....]");

        //DesiredCapabilities dc = new DesiredCapabilities();
        //获取当前服务端口号
        //PhantomJSDriverService defaultService = PhantomJSDriverService.createDefaultService(dc);
        DefaultPhantomJSDriverService defaultPhantomJSDriverService = new DefaultPhantomJSDriverService(exe,port,args,env);

        return defaultPhantomJSDriverService.getPhantomJSDriverService();
	}

	@Override
	protected WebDriver initDriver(DriverService ds, MutableCapabilities op) {

        DesiredCapabilities dc = new DesiredCapabilities();
        
        //添加userAgent
        dc.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX+"userAgent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.67 Safari/537.36");
        dc.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX+"loadImages",false);
        /* ------设置浏览器请求头 ------*/
        //dc.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "Accept",
               //"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        //dc.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "Accept-Language",
               //"zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        //dc.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "Connection", "keep-alive");

        // （以毫秒为单位）定义了超时之后所请求的资源将停止尝试并继续处
        dc.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX+"resourceTimeout", "10000");
        dc.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX+"javascriptEnabled ",false);

        PhantomJSDriver driver = new PhantomJSDriver((PhantomJSDriverService) ds, dc);
        this.driver = driver;
        //为浏览器设置JSEnable、ImgEnable等配置信息(只能通过js实现的浏览器配置)
        //setPageConfig(null);

        return driver;
	}
    /**
     * 为浏览器设置JSEnable、ImgEnable等配置信息(只能通过js实现的浏览器配置)
     *
     * @param config
     */
    @Override
    public void setPageConfig(BrowserConfig config) {
        PhantomJSDriver driver = (PhantomJSDriver) this.driver;
        String sc = "var webPage = require('webpage');\n" + "var page = webPage.create();\n"
                + "page.settings.userAgent = 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36';"
                +"page.setting.javascriptEnabled=false";
        Object script2 = driver.executePhantomJS(sc );

        System.out.println(" script2:"+script2);
    }

}
