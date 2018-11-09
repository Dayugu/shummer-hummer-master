package com.hummer.browser.chrome;

public enum ChromeOptionValues {

	HEADLESS("--headless"), INCOGNITO("--incognito"), DISJS("--disable-javascript"), USERAGENT("--user-agent="),
	DISPLUGINS("--disable-plugins"), DISJAVA("--disable-java"), WINMAX("--start-maximized"), NOSANDBOX("--no-sandbox"),
	SINGLEPROCESS("--single-process"), PROCESSSITE("--process-per-site"), PROCESSTAB("--process-per-tab"),
	DISPOPUPBLOCK("--disable-popup-blocking"), DISIMG("-disable-images"), LANGCN("--lang=zh-CN"),
	DISEXTEND("--disable-extensions"), DISNOTF("--disable-notifications"), WINSIZE("--window-szie="),
	PREF_JS_KEY("profile.default_content_setting_values.javascript"), 
	PREF_IMG_KEY("profile.default_content_setting_values.images"),
	PREF_GEO_KEY("profile.default_content_setting_values.geolocation"),
	PREF_BSYNC_KEY("profile.default_content_setting_values.background_sync"),
	PREF_CAMERA_KEY("profile.default_content_setting_values.media_stream_camera"),
	PREF_MIC_KEY("profile.default_content_setting_values.media_stream_mic"),
	PREF_NOTF_KEY("profile.default_content_setting_values.notifications"),
	PREF_PLUGIN_KEY("profile.default_content_setting_values.plugins"),
	PREF_SOUND_KEY("profile.default_content_setting_values.sound"),
	PREF_KEY("prefs"),PREF_DIS_VALUE(2);

	private Object value;

	ChromeOptionValues(Object value) {
		this.value = value;
	}

	public Object getValue() {
		return this.value;
	}

	public String toString() {
		return this.value.toString();
	}
}
