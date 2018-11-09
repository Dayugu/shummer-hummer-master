package com.hummer.browser;

import org.jsoup.nodes.Document;

public interface Page {
	//更新当前网页引用
	public void update(String pageSource,String baseURI);

	public Document getDocument();
	//获取网页源码
	public String html();
	//获取当前域名
	public String domain();
	//获取当前页面的BaseURI
	public String baseURI();
	//获取当前页面title
	public String title();
	//获取当前页面keywords
	public String keywords();
	//获取当前页面description
	public String description();
	//获取当前页面baseURI
	public String getBaseURI();
	//获取当前网页favorite
//	public String favorite();
	//判断元素是否存在
	public boolean exist(String selector);
	//提取元素文本
	public String text(String selector);
	public String[] texts(String selector);
	public String text(String baseSelector,String childSelector);
	
	//提取元素包含链接,可以是a标签链接，也可以是src属性链接
	public String link(String selector);
	public String[] links(String selector);
	public String link(String baseSelector,String childSelector);
	//提取元素包含属性
	public String attribute(String selector,String name);
	public String[] attributes(String selector,String name);
	public String attribute(String baseSelector,String childSelector,String name);
	
}
