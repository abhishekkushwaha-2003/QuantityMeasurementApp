package com.apps.quantitymeasurementapp;

import com.apps.quantitymeasurementapp.controller.QuantityMeasurementController;
import com.apps.quantitymeasurementapp.entity.QuantityDTO;
import com.apps.quantitymeasurementapp.exception.QuantityMeasurementException;
import com.apps.quantitymeasurementapp.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurementapp.repository.QuantityMeasurementCacheRepository;
import com.apps.quantitymeasurementapp.service.IQuantityMeasurementService;
import com.apps.quantitymeasurementapp.service.QuantityMeasurementServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QuantityMeasurementAppTest {

    private QuantityMeasurementController controller;

    @BeforeEach
    public void setUp() {
        IQuantityMeasurementRepository repository = QuantityMeasurementCacheRepository.getInstance();
        IQuantityMeasurementService service = new QuantityMeasurementServiceImpl(repository);
        controller = new QuantityMeasurementController(service);
    }

    // --- 1. EQUALITY TESTS ---
    @Test
    public void lengthFeetEqualsInches() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LengthUnit");
        QuantityDTO q2 = new QuantityDTO(12.0, "INCHES", "LengthUnit");
        assertTrue(controller.performComparison(q1, q2));
    }

    @Test
    public void lengthYardsEqualsFeet() {
        QuantityDTO q1 = new QuantityDTO(1.0, "YARD", "LengthUnit");
        QuantityDTO q2 = new QuantityDTO(3.0, "FEET", "LengthUnit");
        assertTrue(controller.performComparison(q1, q2));
    }

    @Test
    public void weightKilogramEqualsGrams() {
        QuantityDTO q1 = new QuantityDTO(1.0, "KILOGRAM", "WeightUnit");
        QuantityDTO q2 = new QuantityDTO(1000.0, "GRAM", "WeightUnit");
        assertTrue(controller.performComparison(q1, q2));
    }

    @Test
    public void weightPoundEqualsGrams() {
        QuantityDTO q1 = new QuantityDTO(1.0, "POUND", "WeightUnit");
        QuantityDTO q2 = new QuantityDTO(453.592, "GRAM", "WeightUnit");
        assertTrue(controller.performComparison(q1, q2));
    }

    @Test
    public void volumeLiterEqualsMilliliters() {
        QuantityDTO q1 = new QuantityDTO(1.0, "LITRE", "VolumeUnit");
        QuantityDTO q2 = new QuantityDTO(1000.0, "MILLILITRE", "VolumeUnit");
        assertTrue(controller.performComparison(q1, q2));
    }

    @Test
    public void testTemperatureUnitComparison() {
        QuantityDTO q1 = new QuantityDTO(100.0, "CELSIUS", "TemperatureUnit");
        QuantityDTO q2 = new QuantityDTO(212.0, "FAHRENHEIT", "TemperatureUnit");
        assertTrue(controller.performComparison(q1, q2));
    }

    @Test
    public void preventCrossTypeComparisonLengthVsWeight() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LengthUnit");
        QuantityDTO q2 = new QuantityDTO(1.0, "KILOGRAM", "WeightUnit");
        assertFalse(controller.performComparison(q1, q2));
    }

    // --- 2. BACKWARD COMPATIBILITY EQUALITY TESTS ---
    @Test
    public void backwardCompatibilityLengthFeetEqualsInches() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LengthUnit");
        QuantityDTO q2 = new QuantityDTO(12.0, "INCHES", "LengthUnit");
        assertTrue(controller.performComparison(q1, q2));
    }

    @Test
    public void backwardCompatibilityLengthYardsEqualsFeet() {
        QuantityDTO q1 = new QuantityDTO(1.0, "YARD", "LengthUnit");
        QuantityDTO q2 = new QuantityDTO(3.0, "FEET", "LengthUnit");
        assertTrue(controller.performComparison(q1, q2));
    }

    @Test
    public void backwardCompatibilityWeightKilogramEqualsGrams() {
        QuantityDTO q1 = new QuantityDTO(1.0, "KILOGRAM", "WeightUnit");
        QuantityDTO q2 = new QuantityDTO(1000.0, "GRAM", "WeightUnit");
        assertTrue(controller.performComparison(q1, q2));
    }

    @Test
    public void backwardCompatibilityWeightPoundEqualsGrams() {
        QuantityDTO q1 = new QuantityDTO(1.0, "POUND", "WeightUnit");
        QuantityDTO q2 = new QuantityDTO(453.592, "GRAM", "WeightUnit");
        assertTrue(controller.performComparison(q1, q2));
    }

    // --- 3. CONVERSION TESTS ---
    @Test
    public void convertLengthFeetToInches() {
        QuantityDTO q1 = new QuantityDTO(2.0, "FEET", "LengthUnit");
        QuantityDTO target = new QuantityDTO(0.0, "INCHES", "LengthUnit");
        QuantityDTO result = controller.performConversion(q1, target);
        assertEquals(24.0, result.getValue(), 0.01);
    }

    @Test
    public void convertLengthYardsToInches() {
        QuantityDTO q1 = new QuantityDTO(2.0, "YARD", "LengthUnit");
        QuantityDTO target = new QuantityDTO(0.0, "INCHES", "LengthUnit");
        QuantityDTO result = controller.performConversion(q1, target);
        assertEquals(72.0, result.getValue(), 0.01);
    }

    @Test
    public void convertWeightKilogramsToGrams() {
        QuantityDTO q1 = new QuantityDTO(1.5, "KILOGRAM", "WeightUnit");
        QuantityDTO target = new QuantityDTO(0.0, "GRAM", "WeightUnit");
        QuantityDTO result = controller.performConversion(q1, target);
        assertEquals(1500.0, result.getValue(), 0.01);
    }

    @Test
    public void convertVolumeLitersToMilliliters() {
        QuantityDTO q1 = new QuantityDTO(2.5, "LITRE", "VolumeUnit");
        QuantityDTO target = new QuantityDTO(0.0, "MILLILITRE", "VolumeUnit");
        QuantityDTO result = controller.performConversion(q1, target);
        assertEquals(2500.0, result.getValue(), 0.01);
    }

    @Test
    public void testTemperatureUnitConversion() {
        QuantityDTO q1 = new QuantityDTO(0.0, "CELSIUS", "TemperatureUnit");
        QuantityDTO target = new QuantityDTO(0.0, "FAHRENHEIT", "TemperatureUnit");
        QuantityDTO result = controller.performConversion(q1, target);
        assertEquals(32.0, result.getValue(), 0.01);
    }

    @Test
    public void preventCrossTypeConversionLengthToWeight() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LengthUnit");
        QuantityDTO target = new QuantityDTO(0.0, "KILOGRAM", "WeightUnit");
        assertThrows(QuantityMeasurementException.class, () -> {
            controller.performConversion(q1, target);
        });
    }

    @Test
    public void backwardCompatibilityConvertLengthFeetToInches() {
        QuantityDTO q1 = new QuantityDTO(2.0, "FEET", "LengthUnit");
        QuantityDTO target = new QuantityDTO(0.0, "INCHES", "LengthUnit");
        QuantityDTO result = controller.performConversion(q1, target);
        assertEquals(24.0, result.getValue(), 0.01);
    }

    @Test
    public void backwardCompatibilityConvertWeightKilogramsToGrams() {
        QuantityDTO q1 = new QuantityDTO(1.5, "KILOGRAM", "WeightUnit");
        QuantityDTO target = new QuantityDTO(0.0, "GRAM", "WeightUnit");
        QuantityDTO result = controller.performConversion(q1, target);
        assertEquals(1500.0, result.getValue(), 0.01);
    }

    // --- 4. ADDITION TESTS ---
    @Test
    public void addLengthFeetAndInches() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LengthUnit");
        QuantityDTO q2 = new QuantityDTO(24.0, "INCHES", "LengthUnit");
        QuantityDTO result = controller.performAddition(q1, q2);
        assertEquals(3.0, result.getValue(), 0.01);
    }

    @Test
    public void addLengthYardsAndFeet() {
        QuantityDTO q1 = new QuantityDTO(1.0, "YARD", "LengthUnit");
        QuantityDTO q2 = new QuantityDTO(3.0, "FEET", "LengthUnit");
        QuantityDTO result = controller.performAddition(q1, q2);
        assertEquals(2.0, result.getValue(), 0.01);
    }

    @Test
    public void addWeightKilogramsAndGrams() {
        QuantityDTO q1 = new QuantityDTO(1.0, "KILOGRAM", "WeightUnit");
        QuantityDTO q2 = new QuantityDTO(2000.0, "GRAM", "WeightUnit");
        QuantityDTO result = controller.performAddition(q1, q2);
        assertEquals(3.0, result.getValue(), 0.01);
    }

    @Test
    public void addWeightKilogramsAndPounds() {
        QuantityDTO q1 = new QuantityDTO(1.0, "KILOGRAM", "WeightUnit");
        QuantityDTO q2 = new QuantityDTO(2.20462, "POUND", "WeightUnit");
        QuantityDTO result = controller.performAddition(q1, q2);
        assertEquals(2.0, result.getValue(), 0.01);
    }

    @Test
    public void addVolumeLitersAndMilliliters() {
        QuantityDTO q1 = new QuantityDTO(1.0, "LITRE", "VolumeUnit");
        QuantityDTO q2 = new QuantityDTO(1000.0, "MILLILITRE", "VolumeUnit");
        QuantityDTO result = controller.performAddition(q1, q2);
        assertEquals(2.0, result.getValue(), 0.01);
    }

    @Test
    public void preventCrossTypeAdditionLengthVsWeight() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LengthUnit");
        QuantityDTO q2 = new QuantityDTO(1.0, "KILOGRAM", "WeightUnit");
        assertThrows(QuantityMeasurementException.class, () -> {
            controller.performAddition(q1, q2);
        });
    }

    @Test
    public void backwardCompatibilityAddLengthInSameUnit() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LengthUnit");
        QuantityDTO q2 = new QuantityDTO(2.0, "FEET", "LengthUnit");
        QuantityDTO result = controller.performAddition(q1, q2);
        assertEquals(3.0, result.getValue(), 0.01);
    }

    @Test
    public void backwardCompatibilityAddWeightInSameUnit() {
        QuantityDTO q1 = new QuantityDTO(1.0, "KILOGRAM", "WeightUnit");
        QuantityDTO q2 = new QuantityDTO(2.0, "KILOGRAM", "WeightUnit");
        QuantityDTO result = controller.performAddition(q1, q2);
        assertEquals(3.0, result.getValue(), 0.01);
    }

    @Test
    public void backwardCompatibilityChainedAdditionsLength() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LengthUnit");
        QuantityDTO q2 = new QuantityDTO(1.0, "FEET", "LengthUnit");
        QuantityDTO result1 = controller.performAddition(q1, q2);
        QuantityDTO q3 = new QuantityDTO(12.0, "INCHES", "LengthUnit");
        QuantityDTO result2 = controller.performAddition(result1, q3);
        assertEquals(3.0, result2.getValue(), 0.01);
    }

    // --- 5. SUBTRACTION TESTS ---
    @Test
    public void testSubtractionOfWeightsInSameUnit() {
        QuantityDTO q1 = new QuantityDTO(5.0, "KILOGRAM", "WeightUnit");
        QuantityDTO q2 = new QuantityDTO(2.0, "KILOGRAM", "WeightUnit");
        QuantityDTO result = controller.performSubtraction(q1, q2);
        assertEquals(3.0, result.getValue(), 0.01);
    }

    @Test
    public void testSubtractionOfWeightsWithDifferentUnits() {
        QuantityDTO q1 = new QuantityDTO(5.0, "KILOGRAM", "WeightUnit");
        QuantityDTO q2 = new QuantityDTO(2000.0, "GRAM", "WeightUnit");
        QuantityDTO result = controller.performSubtraction(q1, q2);
        assertEquals(3.0, result.getValue(), 0.01);
    }

    @Test
    public void testSubtractionWithTargetUnit() {
        QuantityDTO q1 = new QuantityDTO(5.0, "KILOGRAM", "WeightUnit");
        QuantityDTO q2 = new QuantityDTO(2000.0, "GRAM", "WeightUnit");
        QuantityDTO target = new QuantityDTO(0.0, "GRAM", "WeightUnit");
        QuantityDTO result = controller.performSubtraction(q1, q2, target);
        assertEquals(3000.0, result.getValue(), 0.01);
    }

    @Test
    public void testSubtractionOfVolumesInSameUnit() {
        QuantityDTO q1 = new QuantityDTO(5.0, "LITRE", "VolumeUnit");
        QuantityDTO q2 = new QuantityDTO(2.0, "LITRE", "VolumeUnit");
        QuantityDTO result = controller.performSubtraction(q1, q2);
        assertEquals(3.0, result.getValue(), 0.01);
    }

    @Test
    public void testSubtractionOfVolumesWithDifferentUnits() {
        QuantityDTO q1 = new QuantityDTO(5.0, "LITRE", "VolumeUnit");
        QuantityDTO q2 = new QuantityDTO(2000.0, "MILLILITRE", "VolumeUnit");
        QuantityDTO result = controller.performSubtraction(q1, q2);
        assertEquals(3.0, result.getValue(), 0.01);
    }

    // --- 6. DIVISION TESTS ---
    @Test
    public void testDivisionOfWeightsInSameUnit() {
        QuantityDTO q1 = new QuantityDTO(10.0, "KILOGRAM", "WeightUnit");
        QuantityDTO q2 = new QuantityDTO(2.0, "KILOGRAM", "WeightUnit");
        double result = controller.performDivision(q1, q2);
        assertEquals(5.0, result, 0.01);
    }

    @Test
    public void testDivisionOfWeightsWithDifferentUnits() {
        QuantityDTO q1 = new QuantityDTO(10.0, "KILOGRAM", "WeightUnit");
        QuantityDTO q2 = new QuantityDTO(2000.0, "GRAM", "WeightUnit");
        double result = controller.performDivision(q1, q2);
        assertEquals(5.0, result, 0.01);
    }

    @Test
    public void testDivisionWithTargetUnit() {
        QuantityDTO q1 = new QuantityDTO(10.0, "FEET", "LengthUnit");
        QuantityDTO q2 = new QuantityDTO(2.0, "FEET", "LengthUnit");
        double result = controller.performDivision(q1, q2);
        assertEquals(5.0, result, 0.01);
    }

    @Test
    public void testDivisionOfVolumesInSameUnit() {
        QuantityDTO q1 = new QuantityDTO(10.0, "LITRE", "VolumeUnit");
        QuantityDTO q2 = new QuantityDTO(5.0, "LITRE", "VolumeUnit");
        double result = controller.performDivision(q1, q2);
        assertEquals(2.0, result, 0.01);
    }

    @Test
    public void testDivisionOfVolumesWithDifferentUnits() {
        QuantityDTO q1 = new QuantityDTO(2000.0, "MILLILITRE", "VolumeUnit");
        QuantityDTO q2 = new QuantityDTO(0.5, "LITRE", "VolumeUnit");
        double result = controller.performDivision(q1, q2);
        assertEquals(4.0, result, 0.01);
    }

    // --- 7. UNSUPPORTED OPERATIONS (TEMPERATURE) ---
    @Test
    public void testTemperatureUnitUnsupportedAddition() {
        QuantityDTO q1 = new QuantityDTO(25.0, "CELSIUS", "TemperatureUnit");
        QuantityDTO q2 = new QuantityDTO(77.0, "FAHRENHEIT", "TemperatureUnit");
        assertThrows(UnsupportedOperationException.class, () -> {
            controller.performAddition(q1, q2);
        });
    }

    @Test
    public void testTemperatureUnitUnsupportedSubtraction() {
        QuantityDTO q1 = new QuantityDTO(25.0, "CELSIUS", "TemperatureUnit");
        QuantityDTO q2 = new QuantityDTO(77.0, "FAHRENHEIT", "TemperatureUnit");
        assertThrows(UnsupportedOperationException.class, () -> {
            controller.performSubtraction(q1, q2);
        });
    }

    @Test
    public void testTemperatureUnitUnsupportedDivision() {
        QuantityDTO q1 = new QuantityDTO(25.0, "CELSIUS", "TemperatureUnit");
        QuantityDTO q2 = new QuantityDTO(77.0, "FAHRENHEIT", "TemperatureUnit");
        assertThrows(UnsupportedOperationException.class, () -> {
            controller.performDivision(q1, q2);
        });
    }
}