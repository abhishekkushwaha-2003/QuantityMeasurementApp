package com.apps.quantitymeasurementapp;

import com.apps.quantitymeasurementapp.controller.QuantityMeasurementController;
import com.apps.quantitymeasurementapp.entity.QuantityDTO;
import com.apps.quantitymeasurementapp.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurementapp.repository.QuantityMeasurementCacheRepository;
import com.apps.quantitymeasurementapp.service.QuantityMeasurementServiceImpl;
import com.apps.quantitymeasurementapp.unit.WeightUnit;

public class QuantityMeasurementApp {

	// Singleton instance
	private static QuantityMeasurementApp instance;

	public QuantityMeasurementController controller;
	public IQuantityMeasurementRepository repository;

	// Private constructor to wire up the N-Tier Architecture
	private QuantityMeasurementApp() {
		this.repository = QuantityMeasurementCacheRepository.getInstance();
		QuantityMeasurementServiceImpl service = new QuantityMeasurementServiceImpl(this.repository);
		this.controller = new QuantityMeasurementController(service);
	}

	// Get the singleton instance
	public static QuantityMeasurementApp getInstance() {
		if (instance == null) {
			instance = new QuantityMeasurementApp();
		}
		return instance;
	}

	public static void main(String[] args) {
		QuantityMeasurementApp app = QuantityMeasurementApp.getInstance();
		
		System.out.println("--- Addition Demonstration ---");

		// Test 1: Add 12 INCHES and 12 FEET, target unit CENTIMETERS
		QuantityDTO inchDTO = new QuantityDTO(12.0, "INCHES", "LengthUnit");
		QuantityDTO feetDTO = new QuantityDTO(12.0, "FEET", "LengthUnit");
		QuantityDTO targetDTO = new QuantityDTO(0.0, "CENTIMETERS", "LengthUnit");

		QuantityDTO result1 = app.controller.performAddition(inchDTO, feetDTO, targetDTO);
		System.out.println("Result 1: " + result1);

		// Test 2: Add 1 KILOGRAM and 2000 GRAMS
		QuantityDTO q1 = new QuantityDTO(1.0, WeightUnit.KILOGRAM);
		QuantityDTO q2 = new QuantityDTO(2000.0, WeightUnit.GRAM);
		
		QuantityDTO result2 = app.controller.performAddition(q1, q2);
		System.out.println("Result 2: " + result2);
	}
}