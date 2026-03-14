package com.apps.quantitymeasurement.repository;

import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.exception.DatabaseException;
import com.apps.quantitymeasurement.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class QuantityMeasurementDatabaseRepository implements IQuantityMeasurementRepository {

	private static final Logger logger = Logger.getLogger(QuantityMeasurementDatabaseRepository.class.getName());

	private static final String INSERT_SQL = "INSERT INTO quantity_measurement_entity "
			+ "(operand1, operand2, operation, result, has_error, error_msg) " + "VALUES (?, ?, ?, ?, ?, ?)";

	private static final String SELECT_ALL_SQL = "SELECT * FROM quantity_measurement_entity ORDER BY created_at";

	private static final String SELECT_BY_OPERATION_SQL = "SELECT * FROM quantity_measurement_entity "
			+ "WHERE UPPER(operation) = UPPER(?) ORDER BY created_at";

	private static final String SELECT_BY_TYPE_SQL = "SELECT * FROM quantity_measurement_entity "
			+ "WHERE operand1 LIKE ? ORDER BY created_at";

	private static final String DELETE_ALL_SQL = "DELETE FROM quantity_measurement_entity";

	private static final String COUNT_SQL = "SELECT COUNT(*) FROM quantity_measurement_entity";

	private final ConnectionPool connectionPool;

	public QuantityMeasurementDatabaseRepository(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
		initializeSchema();
		logger.info("DatabaseRepository initialized");
	}

	private void initializeSchema() {
		String createTable = "CREATE TABLE IF NOT EXISTS quantity_measurement_entity ("
				+ "id         BIGINT AUTO_INCREMENT PRIMARY KEY," + "operand1   VARCHAR(255) NOT NULL,"
				+ "operand2   VARCHAR(255)," + "operation  VARCHAR(50)  NOT NULL," + "result     VARCHAR(255),"
				+ "has_error  BOOLEAN      NOT NULL DEFAULT FALSE," + "error_msg  VARCHAR(500),"
				+ "created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP)";

		Connection conn = connectionPool.acquireConnection();
		try (Statement stmt = conn.createStatement()) {
			stmt.execute(createTable);
			logger.info("Schema initialized");
		} catch (SQLException e) {
			throw new DatabaseException("Schema initialization failed: " + e.getMessage(), "INIT", e);
		} finally {
			connectionPool.releaseConnection(conn);
		}
	}

	@Override
	public void save(QuantityMeasurementEntity entity) {
		Connection conn = connectionPool.acquireConnection();
		try {
			conn.setAutoCommit(false);
			try (PreparedStatement stmt = conn.prepareStatement(INSERT_SQL)) {
				stmt.setString(1, entity.getOperand1());
				stmt.setString(2, entity.getOperand2());
				stmt.setString(3, entity.getOperation());
				stmt.setString(4, entity.getResult());
				stmt.setBoolean(5, entity.hasError());
				stmt.setString(6, entity.getErrorMessage());
				stmt.executeUpdate();
				conn.commit();
				logger.info("Saved entity: " + entity.getOperation());
			}
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
				logger.warning("Rollback failed: " + ex.getMessage());
			}
			throw new DatabaseException("Save failed: " + e.getMessage(), "SAVE", e);
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				logger.warning("Could not reset autocommit: " + e.getMessage());
			}
			connectionPool.releaseConnection(conn);
		}
	}

	@Override
	public List<QuantityMeasurementEntity> getAllMeasurements() {
		List<QuantityMeasurementEntity> results = new ArrayList<>();
		Connection conn = connectionPool.acquireConnection();
		try (PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_SQL); ResultSet rs = stmt.executeQuery()) {
			while (rs.next())
				results.add(mapRow(rs));
			logger.info("Retrieved " + results.size() + " measurements");
		} catch (SQLException e) {
			throw new DatabaseException("getAllMeasurements failed: " + e.getMessage(), "SELECT_ALL", e);
		} finally {
			connectionPool.releaseConnection(conn);
		}
		return results;
	}

	@Override
	public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
		List<QuantityMeasurementEntity> results = new ArrayList<>();
		Connection conn = connectionPool.acquireConnection();
		try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_OPERATION_SQL)) {
			stmt.setString(1, operation);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next())
					results.add(mapRow(rs));
			}
		} catch (SQLException e) {
			throw new DatabaseException("getMeasurementsByOperation failed: " + e.getMessage(), "SELECT_BY_OP", e);
		} finally {
			connectionPool.releaseConnection(conn);
		}
		return results;
	}

	@Override
	public List<QuantityMeasurementEntity> getMeasurementsByType(String type) {
		List<QuantityMeasurementEntity> results = new ArrayList<>();
		Connection conn = connectionPool.acquireConnection();
		try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_TYPE_SQL)) {
			stmt.setString(1, "%" + type.toUpperCase() + "%");
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next())
					results.add(mapRow(rs));
			}
		} catch (SQLException e) {
			throw new DatabaseException("getMeasurementsByType failed: " + e.getMessage(), "SELECT_BY_TYPE", e);
		} finally {
			connectionPool.releaseConnection(conn);
		}
		return results;
	}

	@Override
	public void deleteAll() {
		Connection conn = connectionPool.acquireConnection();
		try {
			conn.setAutoCommit(false);
			try (PreparedStatement stmt = conn.prepareStatement(DELETE_ALL_SQL)) {
				int deleted = stmt.executeUpdate();
				conn.commit();
				logger.info("Deleted " + deleted + " records");
			}
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
				logger.warning("Rollback failed: " + ex.getMessage());
			}
			throw new DatabaseException("deleteAll failed: " + e.getMessage(), "DELETE", e);
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				logger.warning("Could not reset autocommit: " + e.getMessage());
			}
			connectionPool.releaseConnection(conn);
		}
	}

	@Override
	public long getTotalCount() {
		Connection conn = connectionPool.acquireConnection();
		try (PreparedStatement stmt = conn.prepareStatement(COUNT_SQL); ResultSet rs = stmt.executeQuery()) {
			if (rs.next())
				return rs.getLong(1);
		} catch (SQLException e) {
			throw new DatabaseException("getTotalCount failed: " + e.getMessage(), "COUNT", e);
		} finally {
			connectionPool.releaseConnection(conn);
		}
		return 0;
	}

	@Override
	public String getPoolStatistics() {
		return connectionPool.getPoolStatistics();
	}

	@Override
	public void releaseResources() {
		connectionPool.closeAll();
		logger.info("Database resources released");
	}

	private QuantityMeasurementEntity mapRow(ResultSet rs) throws SQLException {
		boolean hasError = rs.getBoolean("has_error");
		if (hasError) {
			return new QuantityMeasurementEntity(rs.getString("operand1"), rs.getString("operand2"),
					rs.getString("operation"), rs.getString("error_msg"), true);
		}
		String operand2 = rs.getString("operand2");
		if (operand2 == null) {
			return new QuantityMeasurementEntity(rs.getString("operand1"), rs.getString("operation"),
					rs.getString("result"));
		}
		return new QuantityMeasurementEntity(rs.getString("operand1"), operand2, rs.getString("operation"),
				rs.getString("result"));
	}
}