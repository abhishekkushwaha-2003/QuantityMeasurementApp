package com.app.quantitymeasurement.entity; 
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.model.QuantityMeasurementEntity;

public class QuantityMeasurementEntityTest {

    @Test
    public void givenValidParameters_WhenEntityCreated_ShouldMapFieldsCorrectly() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
            1.0, "KILOGRAM", "WeightUnit", 
            1000.0, "GRAM", "WeightUnit", 
            "ADD", 2.0, "KILOGRAM", "WeightUnit", 
            "Success", false, "null"
        );

        assertEquals(1.0, entity.getThisValue(), 0.001);
        assertEquals("ADD", entity.getOperation());
        assertEquals(2.0, entity.getResultValue(), 0.001);
        assertEquals("KILOGRAM", entity.getResultUnit());
    }
}