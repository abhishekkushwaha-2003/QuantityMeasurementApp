package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.exception.DatabaseException;
import com.app.quantitymeasurement.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class QuantityMeasurementDatabaseRepository implements IQuantityMeasurementRepository {

	private static final Logger logger = Logger.getLogger(QuantityMeasurementDatabaseRepository.class.getName());
	private static QuantityMeasurementDatabaseRepository instance;

	private QuantityMeasurementDatabaseRepository() {
		initializeDatabase();
	}

	public static synchronized QuantityMeasurementDatabaseRepository getInstance() {
		if (instance == null) {
			instance = new QuantityMeasurementDatabaseRepository();
		}
		return instance;
	}

	private void initializeDatabase() {
		String createSql = "CREATE TABLE IF NOT EXISTS quantity_measurement_entity ("
				+ "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
				+ "this_value DOUBLE NOT NULL, this_unit VARCHAR(50), this_measurement_type VARCHAR(50), "
				+ "that_value DOUBLE, that_unit VARCHAR(50), that_measurement_type VARCHAR(50), "
				+ "operation VARCHAR(20) NOT NULL, result_value DOUBLE, result_unit VARCHAR(50), "
				+ "result_measurement_type VARCHAR(50), result_string VARCHAR(255), is_error BOOLEAN DEFAULT FALSE, "
				+ "error_message VARCHAR(500), created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

		try (Connection conn = ConnectionPool.getInstance().getConnection(); Statement stmt = conn.createStatement()) {
			stmt.execute(createSql);
			logger.info("Database table initialized.");
		} catch (SQLException e) {
			logger.severe("Failed to initialize database: " + e.getMessage());
		}
	}

	@Override
	public void save(QuantityMeasurementEntity entity) {
		String sql = "INSERT INTO quantity_measurement_entity "
				+ "(this_value, this_unit, this_measurement_type, that_value, that_unit, "
				+ "that_measurement_type, operation, result_value, result_unit, "
				+ "result_measurement_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = ConnectionPool.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setDouble(1, entity.thisValue);
			pstmt.setString(2, entity.thisUnit);
			pstmt.setString(3, entity.thisMeasurementType);
			pstmt.setDouble(4, entity.thatValue);
			pstmt.setString(5, entity.thatUnit);
			pstmt.setString(6, entity.thatMeasurementType);
			pstmt.setString(7, entity.operation);
			pstmt.setDouble(8, entity.resultValue);
			pstmt.setString(9, entity.resultUnit);
			pstmt.setString(10, entity.resultMeasurementType);

			pstmt.executeUpdate();
			logger.info("Measurement saved to Database.");
		} catch (SQLException e) {
			throw DatabaseException.queryFailed("SAVE", e);
		}
	}

	@Override
	public List<QuantityMeasurementEntity> getAllMeasurements() {
		List<QuantityMeasurementEntity> list = new ArrayList<>();
		String sql = "SELECT * FROM quantity_measurement_entity";

		try (Connection conn = ConnectionPool.getInstance().getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
				entity.thisValue = rs.getDouble("this_value");
				entity.thisUnit = rs.getString("this_unit");
				entity.operation = rs.getString("operation");
				list.add(entity);
			}
		} catch (SQLException e) {
			throw DatabaseException.queryFailed("GET ALL", e);
		}
		return list;
	}

	@Override
	public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
		return new ArrayList<>();
	}

	@Override
	public List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType) {
		return new ArrayList<>();
	}

	@Override
	public int getTotalCount() {
		try (Connection conn = ConnectionPool.getInstance().getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM quantity_measurement_entity")) {
			if (rs.next())
				return rs.getInt(1);
		} catch (SQLException e) {
			throw DatabaseException.queryFailed("GET COUNT", e);
		}
		return 0;
	}

	@Override
	public void deleteAll() {
		try (Connection conn = ConnectionPool.getInstance().getConnection(); Statement stmt = conn.createStatement()) {
			stmt.executeUpdate("DELETE FROM quantity_measurement_entity");
		} catch (SQLException e) {
			throw DatabaseException.queryFailed("DELETE ALL", e);
		}
	}
}