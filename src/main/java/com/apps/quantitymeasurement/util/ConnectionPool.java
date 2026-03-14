package com.apps.quantitymeasurement.util;

import com.apps.quantitymeasurement.exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ConnectionPool {

	private static final Logger logger = Logger.getLogger(ConnectionPool.class.getName());

	private final List<Connection> availableConnections = new ArrayList<>();
	private final List<Connection> usedConnections = new ArrayList<>();
	private final String url;
	private final String username;
	private final String password;
	private final int maxConnections;

	public ConnectionPool(String url, String username, String password, int maxConnections) {
		this.url = url;
		this.username = username;
		this.password = password;
		this.maxConnections = maxConnections;
		initialize();
	}

	private void initialize() {
		ApplicationConfig config = ApplicationConfig.getInstance();
		try {
			Class.forName(config.getDbDriver());
		} catch (ClassNotFoundException e) {
			logger.warning("DB driver not found: " + e.getMessage());
		}
		int minConnections = config.getMinConnections();
		for (int i = 0; i < minConnections; i++) {
			try {
				availableConnections.add(createConnection());
			} catch (SQLException e) {
				logger.warning("Could not initialize connection " + (i + 1) + ": " + e.getMessage());
			}
		}
		logger.info("Connection pool initialized with " + availableConnections.size() + " connections");
	}

	private Connection createConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	public synchronized Connection acquireConnection() {
		if (!availableConnections.isEmpty()) {
			Connection conn = availableConnections.remove(availableConnections.size() - 1);
			usedConnections.add(conn);
			return conn;
		}
		if (usedConnections.size() < maxConnections) {
			try {
				Connection conn = createConnection();
				usedConnections.add(conn);
				return conn;
			} catch (SQLException e) {
				throw new DatabaseException("Failed to create connection: " + e.getMessage(), "ACQUIRE", e);
			}
		}
		throw new DatabaseException("Connection pool exhausted. Max: " + maxConnections, "ACQUIRE");
	}

	public synchronized void releaseConnection(Connection conn) {
		if (conn != null) {
			usedConnections.remove(conn);
			availableConnections.add(conn);
		}
	}

	public synchronized void closeAll() {
		availableConnections.forEach(this::closeQuietly);
		usedConnections.forEach(this::closeQuietly);
		availableConnections.clear();
		usedConnections.clear();
		logger.info("All connections closed");
	}

	private void closeQuietly(Connection conn) {
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			logger.warning("Error closing connection: " + e.getMessage());
		}
	}

	public synchronized String getPoolStatistics() {
		return "ConnectionPool[active=" + usedConnections.size() + ", available=" + availableConnections.size()
				+ ", max=" + maxConnections + "]";
	}

	public synchronized int getActiveCount() {
		return usedConnections.size();
	}

	public synchronized int getAvailableCount() {
		return availableConnections.size();
	}

	public int getMaxConnections() {
		return maxConnections;
	}
}