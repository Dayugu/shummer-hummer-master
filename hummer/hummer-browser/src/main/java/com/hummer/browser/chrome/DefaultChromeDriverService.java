package com.hummer.browser.chrome;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeDriverService;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class DefaultChromeDriverService extends ChromeDriverService {

	public DefaultChromeDriverService(File executable, int port, ImmutableList<String> args,
			ImmutableMap<String, String> environment) throws IOException {
		super(executable, port, args, environment);
	}

	public static ChromeDriverService createDefaultService(File executable, int port, ImmutableList<String> args,
			ImmutableMap<String, String> environment) throws IOException {
		Builder builder = new Builder();
		builder.usingPort(port);
		builder.usingDriverExecutable(executable);
		builder.withArgs(args).withEnvironment(environment);
		return builder.build();
	}

	public static class Builder extends ChromeDriverService.Builder {

		private ImmutableList<String> args;

		protected Builder withArgs(List<String> arg) {
			if (arg == null)
				args = ImmutableList.of();
			else
				args = ImmutableList.copyOf(arg);
			return this;
		}

		public Builder withEnvironment(Map<String, String> environment) {
			if(environment == null)
				environment = ImmutableMap.of();
			return (Builder)super.withEnvironment(environment);
		}

		protected ImmutableList<String> createArgs() {
			ImmutableList.Builder<String> builder = ImmutableList.builder();
			ImmutableList<String> defaultArgs = super.createArgs();
			if (defaultArgs != null) {
				for (String arg : defaultArgs) {
					builder.add(arg);
				}
			}
			if (args != null) {
				for (String arg : args) {
					builder.add(arg);
				}
			}
			return builder.build();
		}

		protected ChromeDriverService createDriverService(File exe, int port, ImmutableList<String> args,
				ImmutableMap<String, String> environment) {
			return super.createDriverService(exe, port, args, environment);
		}
	}

}
