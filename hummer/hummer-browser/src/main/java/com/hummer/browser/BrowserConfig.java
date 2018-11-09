package com.hummer.browser;

import java.util.Map;
import java.util.UUID;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.net.PortProber;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/**
 * @author xiaoguanghu
 * @since 2018-10-08
 * 
 * 浏览器配置信息基础类
 * */
public abstract class BrowserConfig<OP extends MutableCapabilities> {
	
	public final static String OPTION_HUMMER_PORT = "--hummer-port=";
	public final static String OPTION_HUMMER_ID	= "--hummer-id=";
	
	private String id;				//当前Browser的生成ID
	
	private Platform os;				//当前操作系统名称
	
	private String browserName;		//浏览器名称：chrome、phantomjs、junit、jsoup
	
	private String executeFile;		//driver可执行文件路径
	
	private String browserVersion;	//浏览器版本
	
	private boolean jsEnable = true;		//是否加载和解析JS,默认false
	
	private boolean cssEnable = true;		//是否加载和解析CSS，默认false
	
	private boolean imgEnable = true;		//是否加载图片，默认false
	
	private boolean cacheEnable = true;	//是否使用本地缓存，默认false
	
	private boolean cookieEnable = true;	//使用本地cookie
	
	private String userAgent;		//当前浏览器的UserAgent
	
	private int asyncWait;			//Ajax请求等待时间
	
	private int resouceTimeout;		//元素获取超时时间
	
	private int pageTimeout;		//页面加载超时时间
	
	private boolean headLess = false;		//是否为headLess模式
	
	private int port;				//浏览器启动开放的本地端口
	
	private String windowSize = "800,600";				//浏览器窗口宽度
	
	private boolean maximization  = false;	//窗口最大化,默认为false
	
	private Map<String,String> cookies;		//cookies

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public BrowserConfig<OP> generateId() {
		if(id == null) {
			UUID uid = UUID.randomUUID();
			id = uid.toString().replace("-","");
		}
		return this;
	}

	public Platform getOs() {
		return os;
	}

	public void setOs(Platform os) {
		this.os = os;
	}

	public String getBrowserName() {
		return browserName;
	}

	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	public String getExecuteFile() {
		return executeFile;
	}

	public void setExecuteFile(String executeFile) {
		this.executeFile = executeFile;
	}

	public String getBrowserVersion() {
		return browserVersion;
	}

	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}

	public boolean isJsEnable() {
		return jsEnable;
	}

	public void setJsEnable(boolean jsEnable) {
		this.jsEnable = jsEnable;
	}

	public boolean isCssEnable() {
		return cssEnable;
	}

	public void setCssEnable(boolean cssEnable) {
		this.cssEnable = cssEnable;
	}

	public boolean isImgEnable() {
		return imgEnable;
	}

	public void setImgEnable(boolean imgEnable) {
		this.imgEnable = imgEnable;
	}

	public boolean isCacheEnable() {
		return cacheEnable;
	}

	public void setCacheEnable(boolean cacheEnable) {
		this.cacheEnable = cacheEnable;
	}

	public boolean isCookieEnable() {
		return cookieEnable;
	}

	public void setCookieEnable(boolean cookieEnable) {
		this.cookieEnable = cookieEnable;
	}

	public Map<String, String> getCookies() {
		return cookies;
	}

	public void setCookies(Map<String, String> cookies) {
		this.cookies = cookies;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public int getAsyncWait() {
		return asyncWait;
	}

	public void setAsyncWait(int asyncWait) {
		this.asyncWait = asyncWait;
	}

	public int getResouceTimeout() {
		return resouceTimeout;
	}

	public void setResouceTimeout(int resouceTimeout) {
		this.resouceTimeout = resouceTimeout;
	}

	public int getPageTimeout() {
		return pageTimeout;
	}

	public void setPageTimeout(int pageTimeout) {
		this.pageTimeout = pageTimeout;
	}
	
	public boolean isHeadLess() {
		return headLess;
	}

	public void setHeadLess(boolean headLess) {
		this.headLess = headLess;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isMaximization() {
		return maximization;
	}

	public void setMaximization(boolean maximization) {
		this.maximization = maximization;
	}
	
	public BrowserConfig<OP> usingFreePort() {
		if(this.port == 0)
			this.port = PortProber.findFreePort();
		return this;
	}

	public String getWindowSize() {
		return windowSize;
	}

	public void setWindowSize(String windowSize) {
		this.windowSize = windowSize;
	}
	
	//从配置文件读取配置
//	public abstract void loadConfig();
	//浏览器初始化参数
	public abstract OP createOptions();
	public abstract ImmutableList<String> createArgs();
	//浏览器初始化环境变量
	public abstract ImmutableMap<String,String> createEnv();
	
	
}
