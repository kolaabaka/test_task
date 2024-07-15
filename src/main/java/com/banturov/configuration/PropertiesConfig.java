package com.banturov.configuration;

import java.util.Properties;

import org.apache.logging.log4j.util.PropertiesUtil;

public final class PropertiesConfig {

	private static final Properties PROPERTIES = new Properties();

	static {
		loadProperties();
	}

	private PropertiesConfig() {
	}
	
	public static String get(String key) {
		return PROPERTIES.getProperty(key);
	}

	private static void loadProperties() {
		try (var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
			PROPERTIES.load(inputStream);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
