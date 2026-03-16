package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.unit.IMeasurable;
import com.app.quantitymeasurement.unit.LengthUnit;
import com.app.quantitymeasurement.unit.TemperatureUnit;
import com.app.quantitymeasurement.unit.VolumeUnit;
import com.app.quantitymeasurement.unit.WeightUnit;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    private IMeasurable getUnitEnum(String unit, String measurementType) {
        try {
            switch (measurementType.toUpperCase()) {
                case "LENGTHUNIT": return LengthUnit.valueOf(unit.toUpperCase());
                case "WEIGHTUNIT": return WeightUnit.valueOf(unit.toUpperCase());
                case "VOLUMEUNIT": return VolumeUnit.valueOf(unit.toUpperCase());
                case "TEMPERATUREUNIT": return TemperatureUnit.valueOf(unit.toUpperCase());
                default: throw new IllegalArgumentException("Invalid measurement type");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid unit or measurement type provided");
        }
    }

    private void validateSameCategory(IMeasurable unit1, IMeasurable unit2) {
        if (!unit1.getMeasurableType().equals(unit2.getMeasurableType())) {
            throw new IllegalArgumentException("Operation not possible for two different measurement types!!!");
        }
    }

    @Override
    public boolean compare(QuantityDTO q1, QuantityDTO q2) {
        if (q1 == null || q2 == null) throw new IllegalArgumentException("QuantityDTO objects can't be null!!!");
        
        IMeasurable u1 = getUnitEnum(q1.getUnit(), q1.getMeasurementType());
        IMeasurable u2 = getUnitEnum(q2.getUnit(), q2.getMeasurementType());
        validateSameCategory(u1, u2);

        double baseValue1 = u1.convertToBaseUnit(q1.getValue());
        double baseValue2 = u2.convertToBaseUnit(q2.getValue());
        
        boolean isEquals = Math.abs(baseValue1 - baseValue2) < 0.0001;

        // Save History
        repository.save(new QuantityMeasurementEntity(
            q1.getValue(), q1.getUnit(), q1.getMeasurementType(),
            q2.getValue(), q2.getUnit(), q2.getMeasurementType(),
            "COMPARE", isEquals ? 1.0 : 0.0, "BOOLEAN", "Result"
        ));

        return isEquals;
    }

    @Override
    public QuantityDTO convert(QuantityDTO source, QuantityDTO target) {
        if (source == null || target == null) throw new IllegalArgumentException("QuantityDTO objects can't be null!!!");
        
        IMeasurable sourceUnit = getUnitEnum(source.getUnit(), source.getMeasurementType());
        IMeasurable targetUnit = getUnitEnum(target.getUnit(), target.getMeasurementType());
        validateSameCategory(sourceUnit, targetUnit);

        double baseValue = sourceUnit.convertToBaseUnit(source.getValue());
        double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

        QuantityDTO result = new QuantityDTO(convertedValue, targetUnit.getUnitName(), targetUnit.getMeasurableType());
        
        repository.save(new QuantityMeasurementEntity(
            source.getValue(), source.getUnit(), source.getMeasurementType(),
            0.0, "", "", "CONVERT", 
            result.getValue(), result.getUnit(), result.getMeasurementType()
        ));

        return result;
    }

    @Override
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2, QuantityDTO targetUnitDTO) {
        return executeArithmetic(q1, q2, targetUnitDTO, "ADD");
    }

    @Override
    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2, QuantityDTO targetUnitDTO) {
        return executeArithmetic(q1, q2, targetUnitDTO, "SUBTRACT");
    }

    private QuantityDTO executeArithmetic(QuantityDTO q1, QuantityDTO q2, QuantityDTO targetUnitDTO, String operation) {
        if (q1 == null || q2 == null) throw new IllegalArgumentException("QuantityDTO objects can't be null!!!");
        
        IMeasurable u1 = getUnitEnum(q1.getUnit(), q1.getMeasurementType());
        IMeasurable u2 = getUnitEnum(q2.getUnit(), q2.getMeasurementType());
        validateSameCategory(u1, u2);

        if (!u1.supportsArithmetic() || !u2.supportsArithmetic()) {
            throw new UnsupportedOperationException("Arithmetic operations not supported for this measurement type");
        }

        double baseValue1 = u1.convertToBaseUnit(q1.getValue());
        double baseValue2 = u2.convertToBaseUnit(q2.getValue());
        double resultBaseValue = operation.equals("ADD") ? (baseValue1 + baseValue2) : (baseValue1 - baseValue2);

        IMeasurable targetUnit = u1; // Default target is the first unit
        if (targetUnitDTO != null) {
            targetUnit = getUnitEnum(targetUnitDTO.getUnit(), targetUnitDTO.getMeasurementType());
            validateSameCategory(u1, targetUnit);
        }

        double finalValue = targetUnit.convertFromBaseUnit(resultBaseValue);
        QuantityDTO result = new QuantityDTO(finalValue, targetUnit.getUnitName(), targetUnit.getMeasurableType());

        repository.save(new QuantityMeasurementEntity(
            q1.getValue(), q1.getUnit(), q1.getMeasurementType(),
            q2.getValue(), q2.getUnit(), q2.getMeasurementType(),
            operation, result.getValue(), result.getUnit(), result.getMeasurementType()
        ));

        return result;
    }

    @Override
    public double divide(QuantityDTO q1, QuantityDTO q2) {
        if (q1 == null || q2 == null) throw new IllegalArgumentException("QuantityDTO objects can't be null!!!");
        if (q2.getValue() == 0) throw new ArithmeticException("Cannot divide by zero!!!");

        IMeasurable u1 = getUnitEnum(q1.getUnit(), q1.getMeasurementType());
        IMeasurable u2 = getUnitEnum(q2.getUnit(), q2.getMeasurementType());
        validateSameCategory(u1, u2);

        double baseValue1 = u1.convertToBaseUnit(q1.getValue());
        double baseValue2 = u2.convertToBaseUnit(q2.getValue());
        double result = baseValue1 / baseValue2;

        repository.save(new QuantityMeasurementEntity(
            q1.getValue(), q1.getUnit(), q1.getMeasurementType(),
            q2.getValue(), q2.getUnit(), q2.getMeasurementType(),
            "DIVIDE", result, "RATIO", "RATIO"
        ));

        return result;
    }

    // Overloaded methods for backward compatibility
    @Override public QuantityDTO add(QuantityDTO q1, QuantityDTO q2) { return add(q1, q2, null); }
    @Override public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2) { return subtract(q1, q2, null); }
}