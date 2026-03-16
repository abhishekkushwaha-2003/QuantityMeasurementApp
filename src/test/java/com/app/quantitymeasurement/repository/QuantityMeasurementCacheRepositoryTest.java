package com.app.quantitymeasurement.repository;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

public class QuantityMeasurementCacheRepositoryTest {

    private QuantityMeasurementCacheRepository cacheRepo;

    @Before
    public void setUp() {
        cacheRepo = QuantityMeasurementCacheRepository.getInstance();
        cacheRepo.deleteAll(); 
    }

    @Test
    public void givenEntity_WhenSavedToCache_ShouldIncreaseTotalCount() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.operation = "TEST_CACHE_SAVE";
        
        cacheRepo.save(entity);
        assertEquals(1, cacheRepo.getTotalCount());
    }

    @Test
    public void givenEntitiesInCache_WhenDeleteAllCalled_ShouldEmptyCache() {
        cacheRepo.save(new QuantityMeasurementEntity());
        cacheRepo.deleteAll();
        assertEquals(0, cacheRepo.getTotalCount());
    }
}