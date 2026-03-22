package com.app.quantitymeasurement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;

@Service
public class QuantityMeasurementService {

	@Autowired
	private QuantityMeasurementRepository repository;

	public QuantityMeasurementEntity performConversionAndSave(double value, String unitType, String unit) {

		double convertedValue = value;

		// Feet to Inch
		if (unit != null && unit.equalsIgnoreCase("FEET")) {
			convertedValue = value * 12.0;
		}

		QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

		entity.setThisValue(value); 
		entity.setThisUnit(unit); 
		entity.setThisMeasurementType(unitType); 
		entity.setResultValue(convertedValue); 

		// Default values for other mandatory fields to avoid null constraints
		entity.setOperation("CONVERT");
		entity.setResultString("Converted");
		entity.setError(false);

		return repository.save(entity);
	}
}