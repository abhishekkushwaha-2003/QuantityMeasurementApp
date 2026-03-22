package com.app.quantitymeasurement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import com.app.quantitymeasurement.exception.CategoryMismatchException;

public class QuantityMeasurementServiceImplTest {

    private QuantityMeasurementServiceImpl service;
    private QuantityMeasurementRepository mockRepo;

    @BeforeEach
    public void setUp() {
        mockRepo = mock(QuantityMeasurementRepository.class);
        service = new QuantityMeasurementServiceImpl(mockRepo);
        
        // mocking the save to return a dummy entity
        when(mockRepo.save(any(QuantityMeasurementEntity.class))).thenReturn(new QuantityMeasurementEntity());
    }

    @Test
    public void givenTwoQuantities_WhenAdded_ShouldReturnCorrectSum() {

        QuantityDTO q1 = new QuantityDTO();
        q1.value = 1.0; q1.unit = "KILOGRAM"; q1.measurementType = "WeightUnit";
        
        QuantityDTO q2 = new QuantityDTO();
        q2.value = 2000.0; q2.unit = "GRAM"; q2.measurementType = "WeightUnit";
        
        QuantityDTO target = new QuantityDTO();
        target.value = 0.0; target.unit = "KILOGRAM"; target.measurementType = "WeightUnit";

        QuantityMeasurementDTO result = service.add(q1, q2, target);

        // service returns 3.0 KG (1kg + 2kg)
        assertEquals(3.0, result.resultValue, 0.001);
        verify(mockRepo, times(1)).save(any());
    }

    @Test
    public void givenTwoQuantities_WhenSubtracted_ShouldReturnCorrectDifference() {
        QuantityDTO q1 = new QuantityDTO();
        q1.value = 2.0; q1.unit = "FEET"; q1.measurementType = "LengthUnit";
        
        QuantityDTO q2 = new QuantityDTO();
        q2.value = 12.0; q2.unit = "INCHES"; q2.measurementType = "LengthUnit";
        
        QuantityDTO target = new QuantityDTO();
        target.value = 0.0; target.unit = "FEET"; target.measurementType = "LengthUnit";

        QuantityMeasurementDTO result = service.subtract(q1, q2, target);

        assertEquals(1.0, result.resultValue, 0.001);
        verify(mockRepo, times(1)).save(any());
    }

    @Test
    public void givenDifferentCategories_WhenAdded_ShouldThrowException() {
        QuantityDTO q1 = new QuantityDTO();
        q1.value = 1.0; q1.unit = "KILOGRAM"; q1.measurementType = "WeightUnit";
        
        QuantityDTO q2 = new QuantityDTO();
        q2.value = 1.0; q2.unit = "INCHES"; q2.measurementType = "LengthUnit";

        assertThrows(CategoryMismatchException.class, () -> {
            service.add(q1, q2, null);
        });
    }
}