package com.hummer.browser;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.Rectangle;

/**
 * @author xiaoguanghu
 * @since 2018-10-08
 * 
 *  浏览器引擎接口
 * */
public interface Browser {
	//初始化浏览器
	public void initialize(BrowserConfig<?> config);
    //为浏览器设置参数
	public void setPageConfig(BrowserConfig config);
	//获取当前浏览器上下文引用
	public BrowserContext getContext();
	//清除所有浏览器状态
	public void reset();
	//销毁当前Browser实例，同时关闭RemoteWebdriver和浏览器实例
	public void destory();
	//在当前window窗口，打开新的URL页面
	public Page get(String url,Cookie[] cookies);
	//关闭当前window窗口页面
	public void close();
	//切换当前激活的窗口页面
	public Page switchWindow(String window);
	//切换当前激活的窗口页面,依据URL
	public Page switchWindowByUrl(String url);
	//关闭指定的window窗口页面
	public void closeWindow(String window);
	//获取浏览器当前所有窗口
	public String[] getWindows();
	//获取浏览器当前窗口
	public String getWindow();
	//设置cookie
	public void addCookie(Cookie cookie);
	//删除cookie(当前域名）
	public void deleteCookieNamed(String cookieName);
	//删除cookie，和当前域名无关
	public void deleteCookie(Cookie cookie);
	//删除全部浏览器cookies（和域名无关）
	public void deleteBrowserCookies();
	//删除全部cookie(当前域名，依赖于当前打开的页面）
	public void deleteDomainCookies();
	//切换当前所在Iframe Document域
	public Page switchIframe(String selector);
	//跳出当前所在的iframe document域 返回外层Page
	public Page closeIframe();
	//单击元素
	public Page click(String selector);
	//输入数据
	public void input(String selector,String text);
	//select标签选择
	public void select(String selector,String value);
	//获取当前网页快照图像
	public byte[] snapshot();
	//获取元素相关布局信息
	public Rectangle getLayout(String selector);
}
