package com.hummer.browser;

import java.util.Map;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;

/***
 * @author xiaoguanghu
 * @since 2018-10-08
 * 
 * 当前浏览器上下文数据信息
 * */
public class BrowserContext {
	
	private String id;		//当前Browser实例的ID，全局唯一，用于定位该浏览器实例
	
	private int pid;		//当前浏览器实例的进程ID
	
	private Platform os;		//当前操作系统
	
	private int port;		//当前浏览器使用的本地端口（chrome、phantomjs等无头浏览器）
	
	private int status;		//当前浏览器状态：0无效、1正常、2无响应、3关闭
	
	private Browser browser;	//当前浏览器实例
	
	private WebDriver driver;	//当前浏览器实例所使用的webdriver
	
	private BrowserConfig<?> config;	//当前浏览器所使用的config
	
	private long crateTime;			//浏览器创建时间
	
	private long lastTime;			//浏览器最后一次交互时间
	
	private long closeTime;			//浏览器关闭时间
	
	private Page page;	//当前所有窗口网页内容引用

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Browser getBrowser() {
		return browser;
	}

	public void setBrowser(Browser browser) {
		this.browser = browser;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public long getCrateTime() {
		return crateTime;
	}

	public void setCrateTime(long crateTime) {
		this.crateTime = crateTime;
	}

	public long getLastTime() {
		return lastTime;
	}

	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}

	public long getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(long closeTime) {
		this.closeTime = closeTime;
	}

	public BrowserConfig<?> getConfig() {
		return config;
	}

	public void setConfig(BrowserConfig<?> config) {
		this.config = config;
	}
	
	public Platform getOs() {
		return os;
	}

	public void setOs(Platform os) {
		this.os = os;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public void initProcessIds() {
		String cmd = null;
		if(os.is(Platform.MAC) || os.is(Platform.LINUX) || os.is(Platform.UNIX)) {
			cmd = "ps -ef |grep \""+BrowserConfig.OPTION_HUMMER_ID+id+"\" |grep -v \"grep\" |awk '{print $2,$3}'";
		}else if(os.is(Platform.WINDOWS)){
			
		}
		
		
	}
}
