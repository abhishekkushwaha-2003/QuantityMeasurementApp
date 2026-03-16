package com.app.quantitymeasurement.repository;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import java.util.List;

public class QuantityMeasurementDatabaseRepositoryTest {

    private QuantityMeasurementDatabaseRepository repository;
    private QuantityMeasurementEntity testEntity;

    @BeforeClass
    public static void setUpDatabase() {
        System.setProperty("app.env", "test");
    }

    @Before
    public void setUp() {
        repository = QuantityMeasurementDatabaseRepository.getInstance();
        repository.deleteAll();
        createTestEntity();
    }

    @After
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void testSaveEntity() {
        repository.save(testEntity);
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    public void testGetAllMeasurements() {
        repository.save(testEntity);
        List<QuantityMeasurementEntity> list = repository.getAllMeasurements();
        assertEquals(1, list.size());
    }

    @Test
    public void testDeleteAll() {
        repository.save(testEntity);
        repository.deleteAll();
        assertEquals(0, repository.getTotalCount());
    }

    private void createTestEntity() {
        testEntity = new QuantityMeasurementEntity();
        testEntity.thisValue = 1.0;
        testEntity.thisUnit = "FEET";
        testEntity.thisMeasurementType = "LengthUnit";
        testEntity.operation = "ADD";
        testEntity.resultValue = 12.0;
        testEntity.isError = false;
    }
}