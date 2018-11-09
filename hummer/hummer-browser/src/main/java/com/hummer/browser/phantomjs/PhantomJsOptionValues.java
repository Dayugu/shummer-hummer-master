package com.hummer.browser.phantomjs;

public enum PhantomJsOptionValues {
	
	DISJS("--disable-javascript"), USERAGENT("--user-agent="),
	DISPLUGINS("--disable-plugins"), DISJAVA("--disable-java"), WINMAX("--start-maximized"), NOSANDBOX("--no-sandbox"),
	SINGLEPROCESS("--single-process"), PROCESSSITE("--process-per-site"), PROCESSTAB("--process-per-tab"),
	DISPOPUPBLOCK("--disable-popup-blocking"), DISIMG("--disable-images"), LANGCN("--lang=zh-CN"),
	DISEXTEND("--disable-extensions"), DISNOTF("--disable-notifications"), WINSIZE("--window-szie=");



	private String value;

	PhantomJsOptionValues(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public String toString() {
		return this.value;
	}
}
