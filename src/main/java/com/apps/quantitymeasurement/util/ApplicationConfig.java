package com.apps.quantitymeasurement.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class ApplicationConfig {

	private static final Logger logger = Logger.getLogger(ApplicationConfig.class.getName());

	private static ApplicationConfig instance;
	private final Properties properties = new Properties();

	private ApplicationConfig() {
		loadProperties("application.properties");
		properties.putAll(System.getProperties());
	}

	public static ApplicationConfig getInstance() {
		if (instance == null) {
			instance = new ApplicationConfig();
		}
		return instance;
	}

	private void loadProperties(String fileName) {
		try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
			if (input != null) {
				properties.load(input);
				logger.info("Loaded properties from " + fileName);
			} else {
				logger.warning("Properties file not found: " + fileName);
			}
		} catch (IOException e) {
			logger.severe("Failed to load properties: " + e.getMessage());
		}
	}

	public String get(String key) {
		return properties.getProperty(key);
	}

	public String get(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	public int getInt(String key, int defaultValue) {
		try {
			return Integer.parseInt(properties.getProperty(key, String.valueOf(defaultValue)));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public String getRepositoryType() {
		return get("app.repository.type", "cache");
	}

	public String getDbUrl() {
		return get("db.url");
	}

	public String getDbUsername() {
		return get("db.username");
	}

	public String getDbPassword() {
		return get("db.password");
	}

	public String getDbDriver() {
		return get("db.driver");
	}

	public int getMaxConnections() {
		return getInt("db.pool.maxConnections", 10);
	}

	public int getMinConnections() {
		return getInt("db.pool.minConnections", 2);
	}

	public int getConnectionTimeout() {
		return getInt("db.pool.connectionTimeout", 30000);
	}
}