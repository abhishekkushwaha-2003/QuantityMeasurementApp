package com.app.quantitymeasurement.entity;

import static org.junit.Assert.*;
import org.junit.Test;

public class QuantityMeasurementEntityTest {

    @Test
    public void givenValidParameters_WhenEntityCreated_ShouldMapFieldsCorrectly() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
            1.0, "KILOGRAM", "WeightUnit", 
            1000.0, "GRAM", "WeightUnit",
            "ADD", 2.0, "KILOGRAM", "WeightUnit"
        );

        assertEquals(1.0, entity.thisValue, 0.001);
        assertEquals("ADD", entity.operation);
        assertEquals(2.0, entity.resultValue, 0.001);
        assertEquals("KILOGRAM", entity.resultUnit);
    }
}