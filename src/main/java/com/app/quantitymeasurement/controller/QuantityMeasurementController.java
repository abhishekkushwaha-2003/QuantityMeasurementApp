package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;

public class QuantityMeasurementController {

	private IQuantityMeasurementService service;

	// Dependency Injection via Constructor (UC16 Standard)
	public QuantityMeasurementController(IQuantityMeasurementService service) {
		this.service = service;
	}

	public QuantityDTO performAddition(QuantityDTO q1, QuantityDTO q2, QuantityDTO targetUnitDTO) {
		return service.add(q1, q2, targetUnitDTO);
	}

	public QuantityDTO performSubtraction(QuantityDTO q1, QuantityDTO q2, QuantityDTO targetUnitDTO) {
		return service.subtract(q1, q2, targetUnitDTO);
	}

	public QuantityDTO performConversion(QuantityDTO source, QuantityDTO target) {
		return service.convert(source, target);
	}

	public boolean performComparison(QuantityDTO q1, QuantityDTO q2) {
		return service.compare(q1, q2);
	}

	public double performDivision(QuantityDTO q1, QuantityDTO q2) {
		return service.divide(q1, q2);
	}
}