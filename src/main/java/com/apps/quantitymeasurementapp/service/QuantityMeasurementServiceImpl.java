package com.apps.quantitymeasurementapp.service;

import com.apps.quantitymeasurementapp.entity.QuantityDTO;
import com.apps.quantitymeasurementapp.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurementapp.entity.QuantityModel;
import com.apps.quantitymeasurementapp.exception.CategoryMismatchException;
import com.apps.quantitymeasurementapp.exception.InvalidUnitException;
import com.apps.quantitymeasurementapp.exception.InvalidUnitMeasurementException;
import com.apps.quantitymeasurementapp.exception.QuantityMeasurementException;
import com.apps.quantitymeasurementapp.quantity.Quantity;
import com.apps.quantitymeasurementapp.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurementapp.unit.IMeasurable;
import com.apps.quantitymeasurementapp.unit.LengthUnit;
import com.apps.quantitymeasurementapp.unit.TemperatureUnit;
import com.apps.quantitymeasurementapp.unit.VolumeUnit;
import com.apps.quantitymeasurementapp.unit.WeightUnit;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    private enum Operation {
        COMPARISON, CONVERSION, ADD, ADD_TO_TARGET, SUBTRACT, SUBTRACT_TO_TARGET, DIVIDE;
    }

    // ================= COMPARISON =================

    @Override
    public boolean compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {

        QuantityModel<IMeasurable> m1 = mapToModel(thisQuantityDTO);
        QuantityModel<IMeasurable> m2 = mapToModel(thatQuantityDTO);

        if (m1.getUnit().getClass() != m2.getUnit().getClass()) {
            return false;
        }

        Quantity<IMeasurable> q1 = new Quantity<>(m1.getValue(), m1.getUnit());
        Quantity<IMeasurable> q2 = new Quantity<>(m2.getValue(), m2.getUnit());

        boolean isEqual = q1.equals(q2);

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                thisQuantityDTO.getValue(),
                thisQuantityDTO.getUnit(),
                thisQuantityDTO.getMeasurementType(),
                thatQuantityDTO.getValue(),
                thatQuantityDTO.getUnit(),
                thatQuantityDTO.getMeasurementType(),
                Operation.COMPARISON.name(),
                isEqual ? 1.0 : 0.0,
                "BOOLEAN",
                thisQuantityDTO.getMeasurementType()
        );

        repository.save(entity);

        return isEqual;
    }

    // ================= CONVERSION =================

    @Override
    public QuantityDTO convert(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        return executeArithmetic(thisQuantityDTO, thatQuantityDTO, null, Operation.CONVERSION);
    }

    // ================= ADDITION =================

    @Override
    public QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        return executeArithmetic(thisQuantityDTO, thatQuantityDTO, null, Operation.ADD);
    }

    @Override
    public QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO) {
        return executeArithmetic(thisQuantityDTO, thatQuantityDTO, targetUnitDTO, Operation.ADD_TO_TARGET);
    }

    // ================= SUBTRACTION =================

    @Override
    public QuantityDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        return executeArithmetic(thisQuantityDTO, thatQuantityDTO, null, Operation.SUBTRACT);
    }

    @Override
    public QuantityDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO) {
        return executeArithmetic(thisQuantityDTO, thatQuantityDTO, targetUnitDTO, Operation.SUBTRACT_TO_TARGET);
    }

    // ================= DIVISION =================

    @Override
    public double divide(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        return executeArithmetic(thisQuantityDTO, thatQuantityDTO, null, Operation.DIVIDE).getValue();
    }

    // ================= DTO → MODEL MAPPING =================

    private QuantityModel<IMeasurable> mapToModel(QuantityDTO dto) {

        String type = dto.getMeasurementType();
        String unitName = dto.getUnit();

        IMeasurable unit;

        try {

            switch (type) {

                case "LengthUnit":
                    unit = LengthUnit.valueOf(unitName);
                    break;

                case "VolumeUnit":
                    unit = VolumeUnit.valueOf(unitName);
                    break;

                case "WeightUnit":
                    unit = WeightUnit.valueOf(unitName);
                    break;

                case "TemperatureUnit":
                    unit = TemperatureUnit.valueOf(unitName);
                    break;

                default:
                    throw new InvalidUnitMeasurementException("Invalid Measurement Category: " + type);
            }

        } catch (IllegalArgumentException e) {

            throw new InvalidUnitException("Unit '" + unitName + "' is not valid for " + type);
        }

        return new QuantityModel<>(dto.getValue(), unit);
    }

    // ================= VALIDATION =================

    private void validateModels(QuantityModel<?> m1, QuantityModel<?> m2) {

        if (m1 == null || m2 == null) {
            throw new QuantityMeasurementException("Measurement operands cannot be null");
        }

        if (m1.getUnit().getClass() != m2.getUnit().getClass()) {
            throw new CategoryMismatchException(
                    "Incompatible types: "
                            + m1.getUnit().getClass().getSimpleName()
                            + " vs "
                            + m2.getUnit().getClass().getSimpleName()
            );
        }

        if (!Double.isFinite(m1.getValue()) || !Double.isFinite(m2.getValue())) {
            throw new QuantityMeasurementException("Invalid numeric value provided");
        }
    }

    // ================= MAIN ARITHMETIC ENGINE =================

    private QuantityDTO executeArithmetic(
            QuantityDTO d1,
            QuantityDTO d2,
            QuantityDTO target,
            Operation opType
    ) {

        QuantityModel<IMeasurable> m1 = mapToModel(d1);
        QuantityModel<IMeasurable> m2 = mapToModel(d2);
        QuantityModel<IMeasurable> mT = (target != null) ? mapToModel(target) : null;

        validateModels(m1, m2);

        if (mT != null) {
            validateModels(m1, mT);
        }

        Quantity<IMeasurable> q1 = new Quantity<>(m1.getValue(), m1.getUnit());
        Quantity<IMeasurable> q2 = new Quantity<>(m2.getValue(), m2.getUnit());

        Quantity<IMeasurable> result;

        if (opType.name().contains("ADD")) {

            result = (mT != null)
                    ? q1.add(q2, mT.getUnit())
                    : q1.add(q2);

        }
        else if (opType.name().contains("SUBTRACT")) {

            result = (mT != null)
                    ? q1.subtract(q2, mT.getUnit())
                    : q1.subtract(q2);

        }
        else if (opType == Operation.CONVERSION) {

            double value = q1.convertTo(m2.getUnit());
            result = new Quantity<>(value, m2.getUnit());

        }
        else {

            double value = q1.divide(q2);
            result = new Quantity<>(value, q1.getUnit());
        }

        double resVal = result.getValue();
        String resUnit = result.getUnit().toString();

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                d1.getValue(),
                d1.getUnit(),
                d1.getMeasurementType(),
                d2.getValue(),
                d2.getUnit(),
                d2.getMeasurementType(),
                opType.name(),
                resVal,
                resUnit,
                d1.getMeasurementType()
        );

        repository.save(entity);

        return new QuantityDTO(resVal, resUnit, d1.getMeasurementType());
    }
}