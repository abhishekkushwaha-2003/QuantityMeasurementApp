package com.app.quantitymeasurement;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.app.quantitymeasurement.util.DatabaseConfig;
import com.app.quantitymeasurement.unit.WeightUnit;

import java.util.logging.Logger;

public class QuantityMeasurementApp {

	private static final Logger logger = Logger.getLogger(QuantityMeasurementApp.class.getName());
	private static QuantityMeasurementApp instance;

	public QuantityMeasurementController controller;
	public IQuantityMeasurementRepository repository;

	private QuantityMeasurementApp() {
		// Automatically injects the Database Repository!
		this.repository = QuantityMeasurementDatabaseRepository.getInstance();
		QuantityMeasurementServiceImpl service = new QuantityMeasurementServiceImpl(this.repository);
		this.controller = new QuantityMeasurementController(service);
		logger.info("Application Architected with Database Dependency Injection.");
	}

	public static QuantityMeasurementApp getInstance() {
		if (instance == null) {
			instance = new QuantityMeasurementApp();
		}
		return instance;
	}

	public static void main(String[] args) {
		QuantityMeasurementApp app = QuantityMeasurementApp.getInstance();

		logger.info("--- Executing Math Operations ---");

		QuantityDTO q1 = new QuantityDTO(1.0, WeightUnit.KILOGRAM);
		QuantityDTO q2 = new QuantityDTO(2000.0, WeightUnit.GRAM);
		QuantityDTO targetDTO = new QuantityDTO(0.0, WeightUnit.GRAM);

		app.controller.performAddition(q1, q2, targetDTO);

		logger.info("Operation successful! Check your database to see the saved record.");
		logger.info("Total records in DB: " + app.repository.getTotalCount());
	}
}