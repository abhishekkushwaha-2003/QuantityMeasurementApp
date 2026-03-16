package com.app.quantitymeasurement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ConnectionPool {
	private static final Logger logger = Logger.getLogger(ConnectionPool.class.getName());
	private static ConnectionPool instance;
	private List<Connection> availableConnections = new ArrayList<>();
	private List<Connection> usedConnections = new ArrayList<>();

	private final int poolSize;
	private final String dbUrl;
	private final String dbUsername;
	private final String dbPassword;
	private final String testQuery;

	private ConnectionPool() throws SQLException {
		DatabaseConfig config = DatabaseConfig.getInstance();
		this.dbUrl = config.getProperty(DatabaseConfig.ConfigKey.DB_URL.getKey(), "jdbc:h2:./quantitymeasurementdb");
		this.dbUsername = config.getProperty(DatabaseConfig.ConfigKey.DB_USERNAME.getKey(), "sa");
		this.dbPassword = config.getProperty(DatabaseConfig.ConfigKey.DB_PASSWORD.getKey(), "");
		this.poolSize = config.getIntProperty(DatabaseConfig.ConfigKey.DB_POOL_SIZE.getKey(), 10);
		this.testQuery = config.getProperty(DatabaseConfig.ConfigKey.HIKARI_CONNECTION_TEST_QUERY.getKey(), "SELECT 1");

		try {
			String driverClass = config.getProperty(DatabaseConfig.ConfigKey.DB_DRIVER_CLASS.getKey(), "org.h2.Driver");
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			throw new SQLException("Database driver not found", e);
		}

		for (int i = 0; i < poolSize; i++) {
			availableConnections.add(createConnection());
		}
	}

	public static synchronized ConnectionPool getInstance() throws SQLException {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}

	private Connection createConnection() throws SQLException {
		return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
	}

	public synchronized Connection getConnection() throws SQLException {
		if (availableConnections.isEmpty()) {
			if (usedConnections.size() < poolSize) {
				availableConnections.add(createConnection());
			} else {
				throw new SQLException("Maximum pool size reached!");
			}
		}
		Connection conn = availableConnections.remove(availableConnections.size() - 1);
		usedConnections.add(conn);
		return conn;
	}

	public synchronized void releaseConnection(Connection connection) {
		if (connection != null) {
			usedConnections.remove(connection);
			availableConnections.add(connection);
		}
	}
}