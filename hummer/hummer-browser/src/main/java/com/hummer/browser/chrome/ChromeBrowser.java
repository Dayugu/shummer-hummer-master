package com.hummer.browser.chrome;

import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.service.DriverService;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.hummer.browser.AbstractBrowser;
import com.hummer.browser.BrowserConfig;

public class ChromeBrowser extends AbstractBrowser {

	public ChromeBrowser(BrowserConfig<ChromeOptions> config) {
		super(config);
	}

	public static void main(String[] args) throws Exception {
//		ChromeBrowserConfig chromeConfig = new ChromeBrowserConfig();
//		ChromeBrowser b = new ChromeBrowser(chromeConfig);
//		CommandLine process = new CommandLine(executable);
		
		
	}

	@Override
	protected ChromeDriverService createDefaultDriverService(File exe, int port, ImmutableList<String> args,
			ImmutableMap<String, String> env) {
		try {
			ChromeDriverService service = DefaultChromeDriverService.createDefaultService(exe,port,args,env);
			return service;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected WebDriver initDriver(DriverService ds, MutableCapabilities op) {
		ChromeDriver driver = new ChromeDriver((ChromeDriverService)ds, (ChromeOptions)op);
		return driver;
	}

    @Override
    public void setPageConfig(BrowserConfig config) {

    }
}
