package com.hummer.browser.phantomjs;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.hummer.browser.BrowserConfig;
import com.hummer.browser.chrome.ChromeOptionValues;

public class PhantomJsBrowserConfig extends BrowserConfig<PhantomJsOptions> {

	public PhantomJsBrowserConfig() {
		this.setOs(Platform.getCurrent());
		this.setBrowserName(BrowserType.PHANTOMJS);
		//this.setBrowserVersion("70"); // 来源配置文件
        //设置执行文件路径
        if(this.getOs().equals(Platform.LINUX)){
            this.setExecuteFile("/opt/spider/phantomjs-2.1.1-linux-x86_64/bin/phantomjs");
        }else {
            this.setExecuteFile("G:\\归档\\shummer-hummer-master\\hummer\\hummer-browser\\phantomjs.exe");
        }
		//this.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.67 Safari/537.36");
		this.setCacheEnable(false);
		this.setCssEnable(false);
		this.setHeadLess(false);
		this.setJsEnable(false);
		this.setImgEnable(false);
		//this.setAsyncWait(3 * 1000); // 来源配置文件
		//this.setResouceTimeout(30 * 1000);
		//this.setPageTimeout(10 * 1000); // 来源配置文件
	}

	@Override
	public PhantomJsOptions createOptions() {
		PhantomJsOptions options = new PhantomJsOptions();

//		options.addArguments(ChromeOptionValues.INCOGNITO.getValue());
//		options.addArguments(ChromeOptionValues.DISPLUGINS.getValue());
//		options.addArguments(ChromeOptionValues.DISJAVA.getValue());
//		options.addArguments(ChromeOptionValues.NOSANDBOX.getValue());
//		options.addArguments(ChromeOptionValues.LANGCN.getValue());
//		options.addArguments(ChromeOptionValues.DISEXTEND.getValue());
//		options.addArguments(ChromeOptionValues.DISNOTF.getValue());
//		
//		options.setHeadless(isHeadLess());
//		options.addArguments(OPTION_HUMMER_PORT + getPort());
//		options.addArguments(OPTION_HUMMER_ID + getId());
//		options.setCapability(CapabilityType.PLATFORM_NAME, getOs());
//		options.setCapability(CapabilityType.PLATFORM, getOs());
//		options.setCapability(CapabilityType.BROWSER_NAME, getBrowserName());
//		options.setCapability(CapabilityType.BROWSER_VERSION, getBrowserVersion());
//
//		if (this.isMaximization())
//			options.addArguments(ChromeOptionValues.WINMAX.getValue());
//		else if (this.getWindowSize() != null && this.getWindowSize().length() > 0)
//			options.addArguments(ChromeOptionValues.WINSIZE + this.getWindowSize());
//		if (!this.isJsEnable()) {
//			options.addArguments(ChromeOptionValues.DISJS.getValue());
//			options.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, isJsEnable());
//		}
//		if (!this.isImgEnable())
//			options.addArguments(ChromeOptionValues.DISIMG.getValue());

		return options;
	}

	@Override
	public ImmutableList createArgs() {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        builder.add("--disk-cache="+this.isCacheEnable());
        builder.add("--load-images="+this.isImgEnable());
        /*builder.add("page.settings.userAgent="+this.getUserAgent());
        builder.add("page.settings.loadImages="+this.isImgEnable());
        builder.add("page.settings.javascriptEnabled="+this.isJsEnable());
        builder.add("page.settings.resourceTimeout="+this.getResouceTimeout());*/
        return builder.build();
	}

	@Override
	public ImmutableMap createEnv() {
		return ImmutableMap.of();
	}

}
