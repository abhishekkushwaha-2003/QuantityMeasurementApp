package com.app.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityInputDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.model.QuantityModel;
import com.app.quantitymeasurement.exception.CategoryMismatchException;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.unit.IMeasurable;
import com.app.quantitymeasurement.unit.LengthUnit;

@SpringBootTest
class QuantityMeasurementAppApplicationTests {

    @Autowired
    private QuantityMeasurementController controller;

    //dto test 

    @Test
    public void testQuantityDTO_Structure() {
        // FIX: Manual Setup if constructor is not working
        QuantityDTO quantity = new QuantityDTO();
        quantity.value = 1.0;
        quantity.unit = "FEET";
        quantity.measurementType = "LengthUnit";
        
        assertEquals(1.0, quantity.value);
        assertEquals("FEET", quantity.unit);
        assertEquals("LengthUnit", quantity.measurementType);
    }

    @Test
    void testQuantityModel_Construction() {
        QuantityModel<IMeasurable> model = new QuantityModel<>(5.0, LengthUnit.INCHES);
        assertEquals(5.0, model.getValue());
        assertEquals(LengthUnit.INCHES, model.getUnit());
    }

    // service comparison test

    @Test
    void testService_CompareEquality_SameUnit_Success() {
        QuantityDTO d1 = new QuantityDTO();
        d1.value = 1.0; d1.unit = "FEET"; d1.measurementType = "LengthUnit";
        
        QuantityDTO d2 = new QuantityDTO();
        d2.value = 1.0; d2.unit = "FEET"; d2.measurementType = "LengthUnit";

        QuantityInputDTO qt = new QuantityInputDTO(d1, d2, null);
        
        ResponseEntity<QuantityMeasurementDTO> response = controller.performComparison(qt);
        assertEquals("Equal", response.getBody().resultString);
    }

    @Test
    void testService_CompareEquality_DifferentUnit_Success() {
        QuantityDTO d1 = new QuantityDTO();
        d1.value = 1.0; d1.unit = "FEET"; d1.measurementType = "LengthUnit";
        
        QuantityDTO d2 = new QuantityDTO();
        d2.value = 12.0; d2.unit = "INCHES"; d2.measurementType = "LengthUnit";

        QuantityInputDTO qt = new QuantityInputDTO(d1, d2, null);
        
        ResponseEntity<QuantityMeasurementDTO> response = controller.performComparison(qt);
        assertEquals("Equal", response.getBody().resultString);
    }

    @Test
    void testService_CompareEquality_CrossCategory_Error() {
        QuantityDTO d1 = new QuantityDTO();
        d1.value = 1.0; d1.unit = "FEET"; d1.measurementType = "LengthUnit";
        
        QuantityDTO d2 = new QuantityDTO();
        d2.value = 1.0; d2.unit = "LITRE"; d2.measurementType = "VolumeUnit";

        QuantityInputDTO qt = new QuantityInputDTO(d1, d2, null);
        
        assertThrows(CategoryMismatchException.class, () -> controller.performComparison(qt));
    }

    // conversion and arithmetic test 

    @Test
    void testService_Convert_Success() {
        QuantityDTO d1 = new QuantityDTO();
        d1.value = 1.0; d1.unit = "FEET"; d1.measurementType = "LengthUnit";
        
        QuantityDTO d2 = new QuantityDTO();
        d2.value = 0.0; d2.unit = "INCHES"; d2.measurementType = "LengthUnit";

        QuantityInputDTO qt = new QuantityInputDTO(d1, d2, null);
        
        ResponseEntity<QuantityMeasurementDTO> result = controller.performConversion(qt);
        assertEquals(12.0, result.getBody().resultValue, 0.01);
    }

    @Test
    void testService_Add_WithTargetUnit_Success() {
        QuantityDTO d1 = new QuantityDTO();
        d1.value = 1.0; d1.unit = "FEET"; d1.measurementType = "LengthUnit";
        
        QuantityDTO d2 = new QuantityDTO();
        d2.value = 12.0; d2.unit = "INCHES"; d2.measurementType = "LengthUnit";
        
        QuantityDTO target = new QuantityDTO();
        target.value = 0.0; target.unit = "INCHES"; target.measurementType = "LengthUnit";

        QuantityInputDTO qt = new QuantityInputDTO(d1, d2, target);

        ResponseEntity<QuantityMeasurementDTO> result = controller.performAdditionWithTargetUnit(qt);
        assertEquals(24.0, result.getBody().resultValue, 0.01);
        assertEquals("INCHES", result.getBody().resultUnit);
    }

    @Test
    void testService_Subtract_WithTargetUnit_Success() { 
        QuantityDTO d1 = new QuantityDTO();
        d1.value = 5.0; d1.unit = "KILOGRAMS"; d1.measurementType = "WeightUnit";
        
        QuantityDTO d2 = new QuantityDTO();
        d2.value = 2000.0; d2.unit = "GRAMS"; d2.measurementType = "WeightUnit";
        
        QuantityDTO target = new QuantityDTO();
        target.value = 0.0; target.unit = "GRAMS"; target.measurementType = "WeightUnit";

        QuantityInputDTO qt = new QuantityInputDTO(d1, d2, target);

        ResponseEntity<QuantityMeasurementDTO> result = controller.performSubtractionWithTargetUnit(qt);
        assertEquals(3000.0, result.getBody().resultValue, 0.01);
    }

    // validation exception

    @Test
    void testService_NullEntity_Rejection() {
        QuantityDTO d1 = new QuantityDTO();
        d1.value = 10.0; d1.unit = "FEET"; d1.measurementType = "LengthUnit";
        
        // Passing second quantity as null to trigger exception
        QuantityInputDTO qt = new QuantityInputDTO(d1, null, null);
        assertThrows(QuantityMeasurementException.class, () -> controller.performAddition(qt));
    }
}