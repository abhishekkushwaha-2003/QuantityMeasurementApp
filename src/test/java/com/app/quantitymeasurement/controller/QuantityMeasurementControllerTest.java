package com.app.quantitymeasurement.controller;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.app.quantitymeasurement.unit.LengthUnit;

public class QuantityMeasurementControllerTest {

    private QuantityMeasurementController controller;
    private IQuantityMeasurementService mockService;

    @Before
    public void setUp() {
        mockService = mock(IQuantityMeasurementService.class);
        controller = new QuantityMeasurementController(mockService);
    }

    @Test
    public void givenValidRequest_WhenPerformAdditionCalled_ShouldReturnResultDTO() {
        QuantityDTO q1 = new QuantityDTO(2.0, LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(24.0, LengthUnit.INCHES);
        QuantityDTO target = new QuantityDTO(0.0, LengthUnit.FEET);
        QuantityDTO expectedResult = new QuantityDTO(4.0, LengthUnit.FEET);

        when(mockService.add(any(), any(), any())).thenReturn(expectedResult);

        QuantityDTO result = controller.performAddition(q1, q2, target);

        assertNotNull(result);
        assertEquals(4.0, result.getValue(), 0.001);
        verify(mockService, times(1)).add(any(), any(), any());
    }

    @Test
    public void givenValidRequest_WhenPerformSubtractionCalled_ShouldReturnResultDTO() {
        QuantityDTO q1 = new QuantityDTO(2.0, LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, LengthUnit.INCHES);
        QuantityDTO target = new QuantityDTO(0.0, LengthUnit.FEET);
        QuantityDTO expectedResult = new QuantityDTO(1.0, LengthUnit.FEET);

        when(mockService.subtract(any(), any(), any())).thenReturn(expectedResult);

        QuantityDTO result = controller.performSubtraction(q1, q2, target);

        assertNotNull(result);
        assertEquals(1.0, result.getValue(), 0.001);
        verify(mockService, times(1)).subtract(any(), any(), any());
    }
}