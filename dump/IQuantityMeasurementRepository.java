package com.app.quantitymeasurement.repository;

import java.util.List;

import com.app.quantitymeasurement.model.QuantityMeasurementEntity;

public interface IQuantityMeasurementRepository {
	void save(QuantityMeasurementEntity entity);

	List<QuantityMeasurementEntity> getAllMeasurements();

	// UC16 New Methods for Database
	List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation);

	List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType);

	int getTotalCount();

	void deleteAll();
}