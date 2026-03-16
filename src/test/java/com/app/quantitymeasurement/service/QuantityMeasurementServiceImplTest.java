package com.app.quantitymeasurement.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.unit.LengthUnit;
import com.app.quantitymeasurement.unit.WeightUnit;

public class QuantityMeasurementServiceImplTest {

    private QuantityMeasurementServiceImpl service;
    private IQuantityMeasurementRepository mockRepo;

    @Before
    public void setUp() {
        mockRepo = mock(IQuantityMeasurementRepository.class);
        service = new QuantityMeasurementServiceImpl(mockRepo);
        // Repository DB touch na kare isliye mock setup kiya
        doNothing().when(mockRepo).save(any());
    }

    @Test
    public void givenTwoQuantities_WhenAdded_ShouldReturnCorrectSum() {
        QuantityDTO q1 = new QuantityDTO(1.0, WeightUnit.KILOGRAM);
        QuantityDTO q2 = new QuantityDTO(2000.0, WeightUnit.GRAM);
        QuantityDTO target = new QuantityDTO(0.0, WeightUnit.KILOGRAM);

        QuantityDTO result = service.add(q1, q2, target);

        assertEquals(3.0, result.getValue(), 0.001);
        verify(mockRepo, times(1)).save(any());
    }

    @Test
    public void givenTwoQuantities_WhenSubtracted_ShouldReturnCorrectDifference() {
        QuantityDTO q1 = new QuantityDTO(2.0, LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, LengthUnit.INCHES);
        QuantityDTO target = new QuantityDTO(0.0, LengthUnit.FEET);

        QuantityDTO result = service.subtract(q1, q2, target);

        assertEquals(1.0, result.getValue(), 0.001);
        verify(mockRepo, times(1)).save(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenDifferentCategories_WhenAdded_ShouldThrowException() {
        QuantityDTO q1 = new QuantityDTO(1.0, WeightUnit.KILOGRAM);
        QuantityDTO q2 = new QuantityDTO(1.0, LengthUnit.INCHES);
        
        service.add(q1, q2, null);
    }
}