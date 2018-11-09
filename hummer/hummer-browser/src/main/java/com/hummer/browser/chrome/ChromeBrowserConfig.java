package com.hummer.browser.chrome;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.BrowserType;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.hummer.browser.BrowserConfig;

public class ChromeBrowserConfig extends BrowserConfig<ChromeOptions>{
	
	public ChromeBrowserConfig() {
		this.setOs(Platform.WIN10); 				 //来源配置文件
		this.setBrowserName(BrowserType.CHROME);
		this.setBrowserVersion("70");				//来源配置文件
		this.setExecuteFile("chromedriver");		//来源配置文件
		this.setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.67 Safari/537.36");
		//this.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.67 Safari/537.36");
		//this.setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:62.0) Gecko/20100101 Firefox/62.0");
		this.setAsyncWait(3*1000);					//来源配置文件
		this.setResouceTimeout(3*1000);
		this.setPageTimeout(10*1000); 				//来源配置文件
		this.setHeadLess(false); 					//来源配置文件
		this.setMaximization(false);
		this.setJsEnable(true);
		this.setImgEnable(false);
	}
	
	public ChromeOptions createOptions() {
		ChromeOptions options = new ChromeOptions();
		
		options.addArguments(ChromeOptionValues.INCOGNITO.toString());
		options.addArguments(ChromeOptionValues.DISPLUGINS.toString());
		options.addArguments(ChromeOptionValues.DISJAVA.toString());
		options.addArguments(ChromeOptionValues.NOSANDBOX.toString());
		options.addArguments(ChromeOptionValues.LANGCN.toString());
		options.addArguments(ChromeOptionValues.DISEXTEND.toString());
		options.addArguments(ChromeOptionValues.DISNOTF.toString());
		options.setHeadless(isHeadLess());
		options.addArguments(OPTION_HUMMER_PORT+getPort());
		options.addArguments(OPTION_HUMMER_ID+getId());
		
		Map<String,Object> prefs = new HashMap<String,Object>();
		prefs.put(ChromeOptionValues.PREF_BSYNC_KEY.toString(),ChromeOptionValues.PREF_DIS_VALUE.getValue());
		prefs.put(ChromeOptionValues.PREF_GEO_KEY.toString(),ChromeOptionValues.PREF_DIS_VALUE.getValue());
		prefs.put(ChromeOptionValues.PREF_CAMERA_KEY.toString(),ChromeOptionValues.PREF_DIS_VALUE.getValue());
		prefs.put(ChromeOptionValues.PREF_MIC_KEY.toString(),ChromeOptionValues.PREF_DIS_VALUE.getValue());
		prefs.put(ChromeOptionValues.PREF_NOTF_KEY.toString(),ChromeOptionValues.PREF_DIS_VALUE.getValue());
		prefs.put(ChromeOptionValues.PREF_PLUGIN_KEY.toString(),ChromeOptionValues.PREF_DIS_VALUE.getValue());
		prefs.put(ChromeOptionValues.PREF_SOUND_KEY.toString(),ChromeOptionValues.PREF_DIS_VALUE.getValue());
		
		if(this.isMaximization())
			options.addArguments(ChromeOptionValues.WINMAX.toString());
		else if(this.getWindowSize()!=null && this.getWindowSize().length() >0)
			options.addArguments(ChromeOptionValues.WINSIZE.toString() + this.getWindowSize());
		if(!this.isJsEnable()) {
			options.addArguments(ChromeOptionValues.DISJS.toString());
			prefs.put(ChromeOptionValues.PREF_JS_KEY.toString(),ChromeOptionValues.PREF_DIS_VALUE.getValue());
			
		}
		if(!this.isImgEnable()) {
			options.addArguments(ChromeOptionValues.DISIMG.toString());
			prefs.put(ChromeOptionValues.PREF_IMG_KEY.toString(),ChromeOptionValues.PREF_DIS_VALUE.getValue());
		}
		options.setExperimentalOption(ChromeOptionValues.PREF_KEY.toString(), prefs);
		return options;
	}
	
	public ImmutableList<String> createArgs() {
		return ImmutableList.of();
	}
	
	public ImmutableMap<String,String> createEnv() {
		return ImmutableMap.of();
	}

}
