package com.app.quantitymeasurement.integrationTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.app.quantitymeasurement.unit.VolumeUnit;

public class QuantityMeasurementIntegrationTest {

    private QuantityMeasurementController controller;
    private QuantityMeasurementDatabaseRepository databaseRepo;

    @Before
    public void setUp() {
        databaseRepo = QuantityMeasurementDatabaseRepository.getInstance();
        databaseRepo.deleteAll(); 
        QuantityMeasurementServiceImpl service = new QuantityMeasurementServiceImpl(databaseRepo);
        controller = new QuantityMeasurementController(service);
    }

    @Test
    public void givenFullApplicationStack_WhenMathPerformed_ShouldSaveToDatabase() {
        QuantityDTO gallon = new QuantityDTO(1.0, VolumeUnit.GALLON);
        QuantityDTO liter = new QuantityDTO(3.78, VolumeUnit.LITRE);
        QuantityDTO target = new QuantityDTO(0.0, VolumeUnit.LITRE);

        QuantityDTO result = controller.performAddition(gallon, liter, target);
        
        assertNotNull(result);
        assertEquals(7.56, result.getValue(), 0.05);
        assertEquals(1, databaseRepo.getTotalCount()); // Proves full DB integration works
    }
}