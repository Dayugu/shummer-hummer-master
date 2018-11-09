package com.hummer.browser.phantomjs;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.openqa.selenium.remote.CapabilityType.ACCEPT_INSECURE_CERTS;
import static org.openqa.selenium.remote.CapabilityType.PAGE_LOAD_STRATEGY;
import static org.openqa.selenium.remote.CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR;
import static org.openqa.selenium.remote.CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;

import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;

public class PhantomJsOptions extends MutableCapabilities {

	private static final long serialVersionUID = 4478309541548676265L;

	public static final String CAPABILITY = "hummer:phantomOptions";

	private String binary;
	private List<String> args = new ArrayList<String>();
	private List<File> extensionFiles = new ArrayList<File>();
	private List<String> extensions = new ArrayList<String>();
	private Map<String, Object> experimentalOptions = new HashMap<String, Object>();

	public PhantomJsOptions() {
		setCapability(CapabilityType.BROWSER_NAME, BrowserType.PHANTOMJS);
	}

	@Override
	public PhantomJsOptions merge(Capabilities extraCapabilities) {
		super.merge(extraCapabilities);
		return this;
	}

	public PhantomJsOptions setBinary(File path) {
		binary = checkNotNull(path).getPath();
		return this;
	}

	public PhantomJsOptions setBinary(String path) {
		binary = checkNotNull(path);
		return this;
	}

	public PhantomJsOptions addArguments(String... arguments) {
		addArguments(ImmutableList.copyOf(arguments));
		return this;
	}

	public PhantomJsOptions addArguments(List<String> arguments) {
		args.addAll(arguments);
		return this;
	}

	public PhantomJsOptions addExtensions(File... paths) {
		addExtensions(ImmutableList.copyOf(paths));
		return this;
	}

	public PhantomJsOptions addExtensions(List<File> paths) {
		for (File path : paths) {
			checkNotNull(path);
			checkArgument(path.exists(), "%s does not exist", path.getAbsolutePath());
			checkArgument(!path.isDirectory(), "%s is a directory", path.getAbsolutePath());
		}
		extensionFiles.addAll(paths);
		return this;
	}

	public PhantomJsOptions addEncodedExtensions(String... encoded) {
		addEncodedExtensions(ImmutableList.copyOf(encoded));
		return this;
	}

	public PhantomJsOptions addEncodedExtensions(List<String> encoded) {
		for (String extension : encoded) {
			checkNotNull(extension);
		}
		extensions.addAll(encoded);
		return this;
	}

	public PhantomJsOptions setExperimentalOption(String name, Object value) {
		experimentalOptions.put(checkNotNull(name), value);
		return this;
	}

	public PhantomJsOptions setPageLoadStrategy(PageLoadStrategy strategy) {
		setCapability(PAGE_LOAD_STRATEGY, strategy);
		return this;
	}

	public PhantomJsOptions setUnhandledPromptBehaviour(UnexpectedAlertBehaviour behaviour) {
		setCapability(UNHANDLED_PROMPT_BEHAVIOUR, behaviour);
		setCapability(UNEXPECTED_ALERT_BEHAVIOUR, behaviour);
		return this;
	}

	public PhantomJsOptions setAcceptInsecureCerts(boolean acceptInsecureCerts) {
		setCapability(ACCEPT_INSECURE_CERTS, acceptInsecureCerts);
		return this;
	}

	public PhantomJsOptions setHeadless(boolean headless) {
		args.remove("--headless");
		if (headless) {
			args.add("--headless");
			args.add("--disable-gpu");
		}
		return this;
	}

	public PhantomJsOptions setProxy(Proxy proxy) {
		setCapability(CapabilityType.PROXY, proxy);
		return this;
	}

	@Override
	protected int amendHashCode() {
		return Objects.hash(args, binary, experimentalOptions, extensionFiles, extensions);
	}

	@Override
	public Map<String, Object> asMap() {
		Map<String, Object> toReturn = new TreeMap<>();
		toReturn.putAll(super.asMap());

		Map<String, Object> options = new TreeMap<>();
		experimentalOptions.forEach(options::put);

		if (binary != null) {
			options.put("binary", binary);
		}

		options.put("args", ImmutableList.copyOf(args));

		options.put("extensions", Stream.concat(extensionFiles.stream().map(file -> {
			try {
				return Base64.getEncoder().encodeToString(Files.toByteArray(file));
			} catch (IOException e) {
				throw new SessionNotCreatedException(e.getMessage(), e);
			}
		}), extensions.stream()).collect(ImmutableList.toImmutableList()));

		toReturn.put(CAPABILITY, options);

		return Collections.unmodifiableMap(toReturn);
	}
}
