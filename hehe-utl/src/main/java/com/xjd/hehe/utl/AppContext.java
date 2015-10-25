package com.xjd.hehe.utl;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * 应用上下文环境
 * 
 * @author elvis.xu
 * @since 2015-6-4
 */
public class AppContext {

	protected static AppContext instance;

	protected Properties properties;

	public AppContext(Properties properties) {
		if (instance == null) {
			instance = this;
			this.properties = properties;
		}
	}

	public static String getProperty(String key) {
		if (instance.properties == null) {
			return null;
		}
		return instance.properties.getProperty(key);
	}

	public static boolean isEnvProduct() {
		return "product".equalsIgnoreCase(getProperty("env"));
	}

	public static boolean isPwdField(String name) {
		name = StringUtils.trimToNull(name);

		if (name != null
				&& (StringUtils.endsWithIgnoreCase(name, "pwd") || StringUtils.endsWithIgnoreCase(name, "password") || StringUtils
						.endsWithIgnoreCase(name, "pass"))) {
			return true;
		}
		return false;
	}

	public static String getPwdMask() {
		return "[******]";
	}

	public static String getDefaultPort() {
		return getProperty("api.port");
	}
}
