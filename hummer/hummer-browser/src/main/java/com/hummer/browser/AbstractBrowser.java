package com.hummer.browser;

import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.hummer.browser.util.Constants;
import com.hummer.browser.util.KillServer;
import org.openqa.selenium.*;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.service.DriverService;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public abstract class AbstractBrowser implements Browser {

	protected WebDriver driver = null;
	protected BrowserContext context = null;
	protected BrowserConfig<?> bconfig = null;

	public AbstractBrowser(BrowserConfig<?> config) {
		initialize(config);
	}

	public void initialize(BrowserConfig<?> config) {
		bconfig = config;
		int port = bconfig.generateId().usingFreePort().getPort();
		String exepath = bconfig.getExecuteFile();
		File exe = new File(exepath);
		ImmutableList<String> args = bconfig.createArgs();
		ImmutableMap<String, String> envs = bconfig.createEnv();
		MutableCapabilities options = bconfig.createOptions();
		DriverService ds = createDefaultDriverService(exe, port, args, envs);
		//初始化webDriver
		this.driver = initDriver(ds, options);
		//為webDriver设置参数
        //setPageConfig(config);

		context = initContext();
	}


	protected abstract DriverService createDefaultDriverService(File exe, int port, ImmutableList<String> args,
			ImmutableMap<String, String> env);

	protected abstract WebDriver initDriver(DriverService ds, MutableCapabilities op);

	protected BrowserContext initContext() {
		synchronized (this) {
			if (context == null) {
				context = new BrowserContext();
				context.setBrowser(this);
				context.setDriver(driver);
				context.setConfig(bconfig);
				context.setPort(bconfig.getPort());
				context.setId(bconfig.getId());
				context.setOs(Platform.getCurrent());
				context.setPage(new DocumentPage());
			}
			return context;
		}
	}

	public BrowserContext getContext() {
		return context;
	}

	public void reset() {
		String[] windows = getWindows();

		for (int i = 0; i < windows.length - 1; i++) {
			driver.switchTo().window(windows[i]);
			driver.manage().deleteAllCookies();
			driver.close();
		}
		driver.switchTo().window(windows[windows.length - 1]);
		driver.manage().deleteAllCookies();
		driver.navigate().to("about:blank");
	}

	// 关闭所有窗口，初始化浏览器
	public void close() {
		reset();
	}

	public void destory() {

        //逻辑需重新实现
        //当quit()执行后，该浏览器对应的进程仍存在的时候，需要调用shell命令kill进程

        //关闭浏览器
        //this.webDriver.quit();

        //根据端口号杀死指定浏览器的进程
        int prot = context.getPort();
        System.out.println("destoryBrowser prot: " + prot);
        KillServer killServer = new KillServer(prot);
        killServer.kill();

        //清除浏览器中的残余的WebDriver，
	}

	private void forceDestory() {

	    //根据端口号杀死指定浏览器的进程
        int prot = context.getPort();
        System.out.println("destoryBrowser prot: " + prot);
        KillServer killServer = new KillServer(prot);
        killServer.kill();
	}

	public Page get(String url, Cookie[] cookies) {
		if (cookies != null){
            for(Cookie cookie : cookies) {
                driver.manage().addCookie(cookie);
            }
        }
		driver.get(url);
        System.out.println("TitleName: "+driver.getTitle());
        //更新浏览器操作时间
        this.context.setLastTime(System.currentTimeMillis());

        Page page = new DocumentPage();
        page.update(driver.getPageSource(),url);
        return page;
	}

	public void closeWindow() {
		String[] windows = getWindows();
		if (windows.length > 1) {
			driver.close();
		} else if (windows.length == 1) {
			reset();
		}

	}

	public Page switchWindow(String window) {
        try{
            //获取当前浏览器页面的所有window
            Set<String> windowHandles = this.driver.getWindowHandles();
            //获取当前页面的window
            String currentWindow = this.driver.getWindowHandle();

            for (String page : windowHandles ){
                //如果是当前页面则跳出循环
                if (page.equals(currentWindow)){
                    continue;
                }else {
                    this.driver.switchTo().window(page);

                    if (this.driver.getTitle().contains(window)){
                        System.out.println("Switch to window: "
                                + window + " successfully!");
                        break;

                    }else {
                        continue;
                    }

                }
            }
        }catch (Exception e){
            System.out.println("Switch to window: "
                    + window + " error!");
        }
        return null;
	}

	public Page switchWindowByUrl(String url) {
		return null;
	}

	public void closeWindow(String window) {
		String[] windows = getWindows();
		for (String w : windows) {
			if (w.equals(window)) {
				driver.switchTo().window(window);
				closeWindow();
			}
		}
	}

	public String[] getWindows() {
		return (String[]) driver.getWindowHandles().toArray();

	}

	public String getWindow() {
		return driver.getWindowHandle();

	}

	public void addCookie(Cookie cookie) {
		driver.manage().addCookie(cookie);

	}

	public void deleteCookieNamed(String cookieName) {
		driver.manage().deleteCookieNamed(cookieName);

	}

	public void deleteCookie(Cookie cookie) {
		driver.manage().deleteCookie(cookie);

	}

	public void deleteBrowserCookies() {
		String[] windows = this.getWindows();
		for (String window : windows) {
			driver.switchTo().window(window);
			driver.manage().deleteAllCookies();
		}

	}

	public void deleteDomainCookies() {
		driver.manage().deleteAllCookies();
	}

	// 切换当前所在Iframe Document域
	public Page switchIframe(String selector) {
        //先切换为默认的frame,在切换至指定frame
        this.driver.switchTo().defaultContent();
        WebElement element = this.driver.findElement(By.cssSelector(selector));

        this.driver.switchTo().frame(element);
        return null;
	}

	// 跳出当前所在的iframe document域 返回外层Page
	public Page closeIframe() {
		return null;
	}

	// 单击元素
	public Page click(String selector) {
        WebElement el = this.driver.findElement(By.cssSelector(selector));
        if (Constants.HTML_TAG_A.equalsIgnoreCase(el.getTagName())) {
            JavascriptExecutor jse = (JavascriptExecutor) this.driver;
            jse.executeScript("arguments[0].setAttribute('target','_blank')", el);
        }
        el.click();
        DocumentPage page = new DocumentPage();
        page.update(this.driver.getPageSource(),this.driver.getCurrentUrl());

        return page;
	}

	// 输入数据
	public void input(String selector, String text) {
        WebElement el = this.driver.findElement(By.cssSelector(selector));
        if(Constants.ACTION_TYPE_INPUT.equalsIgnoreCase(el.getTagName())){
            JavascriptExecutor jse = (JavascriptExecutor) this.driver;
            jse.executeScript("document.getElementById('"+selector+"').setAttribute('value',"+text+")", el);
        }
	}

	// select标签选择
	public void select(String selector, String value) {
        WebElement el = this.driver.findElement(By.cssSelector(selector));
        if(Constants.ACTION_ATTR_SELECTOR.equalsIgnoreCase(el.getTagName())){
            JavascriptExecutor jse = (JavascriptExecutor) this.driver;
            //获取被选中的值，并
            jse.executeScript("document.getElementById('"+selector+"').value='"+value+"'");
        }
	}

	// 获取当前网页快照图像
	public byte[] snapshot() {
		return null;
	}

	// 获取元素相关布局信息
	public Rectangle getLayout(String selector) {
		return null;
	}

}
