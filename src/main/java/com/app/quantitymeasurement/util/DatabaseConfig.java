package com.app.quantitymeasurement.util;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class DatabaseConfig {
	private static final Logger logger = Logger.getLogger(DatabaseConfig.class.getName());
	private static DatabaseConfig instance;
	private Properties properties;

	public enum ConfigKey {
		REPOSITORY_TYPE("repository.type"), DB_DRIVER_CLASS("db.driver"), DB_URL("db.url"), DB_USERNAME("db.username"),
		DB_PASSWORD("db.password"), DB_POOL_SIZE("db.hikari.maximum-pool-size"),
		HIKARI_CONNECTION_TEST_QUERY("db.hikari.connection-test-query");

		private final String key;

		ConfigKey(String key) {
			this.key = key;
		}

		public String getKey() {
			return key;
		}
	}

	private DatabaseConfig() {
		loadConfiguration();
	}

	public static synchronized DatabaseConfig getInstance() {
		if (instance == null) {
			instance = new DatabaseConfig();
		}
		return instance;
	}

	private void loadConfiguration() {
		properties = new Properties();
		try {
			String configFile = "/application.properties";
			InputStream input = DatabaseConfig.class.getResourceAsStream(configFile);

			if (input != null) {
				properties.load(input);
				logger.info("Configuration loaded successfully from " + configFile);
			} else {
				logger.warning("application.properties not found! Using default values.");
			}
		} catch (Exception e) {
			logger.severe("Error loading configuration: " + e.getMessage());
		}
	}

	public String getProperty(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	public int getIntProperty(String key, int defaultValue) {
		try {
			return Integer.parseInt(properties.getProperty(key, String.valueOf(defaultValue)));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
}