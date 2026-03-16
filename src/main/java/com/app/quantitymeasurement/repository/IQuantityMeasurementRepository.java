package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import java.util.List;

public interface IQuantityMeasurementRepository {
	void save(QuantityMeasurementEntity entity);

	List<QuantityMeasurementEntity> getAllMeasurements();

	// UC16 New Methods for Database
	List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation);

	List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType);

	int getTotalCount();

	void deleteAll();
}