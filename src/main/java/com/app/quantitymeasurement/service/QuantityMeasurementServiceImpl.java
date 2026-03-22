package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.model.QuantityModel;
import com.app.quantitymeasurement.exception.*;
import com.app.quantitymeasurement.quantity.Quantity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import com.app.quantitymeasurement.unit.*;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.logging.Logger;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

	private static final Logger logger = Logger.getLogger(QuantityMeasurementServiceImpl.class.getName());

	private final QuantityMeasurementRepository repository;

	public QuantityMeasurementServiceImpl(QuantityMeasurementRepository repository) {
		this.repository = repository;
	}

	private enum Operation {
		ADD, SUBTRACT, MULTIPLY, DIVIDE, COMPARE, CONVERT;
	}

	@Override
	public QuantityMeasurementDTO compare(QuantityDTO thisDTO, QuantityDTO thatDTO) {
		QuantityModel<IMeasurable> m1 = mapToModel(thisDTO);
		QuantityModel<IMeasurable> m2 = mapToModel(thatDTO);
		validateModels(m1, m2);

		Quantity<IMeasurable> q1 = new Quantity<>(m1.getValue(), m1.getUnit());
		Quantity<IMeasurable> q2 = new Quantity<>(m2.getValue(), m2.getUnit());

		boolean isEqual = q1.equals(q2);

		// Final Persistence with clean resultUnit
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(thisDTO.value, thisDTO.unit.toUpperCase(),
				thisDTO.measurementType, thatDTO.value, thatDTO.unit.toUpperCase(), thatDTO.measurementType,
				Operation.COMPARE.name(), 0.0, thisDTO.unit.toUpperCase(), thisDTO.measurementType,
				isEqual ? "Equal" : "Not Equal", false, "null");
		repository.save(entity);
		return new QuantityMeasurementDTO().from(entity);
	}

	@Override
	public QuantityMeasurementDTO convert(QuantityDTO thisDTO, QuantityDTO thatDTO) {
		QuantityModel<IMeasurable> m1 = mapToModel(thisDTO);
		QuantityModel<IMeasurable> m2 = mapToModel(thatDTO);

		Quantity<IMeasurable> q1 = new Quantity<>(m1.getValue(), m1.getUnit());
		double convertedValue = q1.convertTo(m2.getUnit());

		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(thisDTO.value, thisDTO.unit.toUpperCase(),
				thisDTO.measurementType, thatDTO.value, thatDTO.unit.toUpperCase(), thatDTO.measurementType,
				Operation.CONVERT.name(), convertedValue, thatDTO.unit.toUpperCase(), thisDTO.measurementType,
				"Converted", false, "null");

		repository.save(entity);
		return new QuantityMeasurementDTO().from(entity);
	}

	@Override
	public QuantityMeasurementDTO add(QuantityDTO d1, QuantityDTO d2) {
		return executeArithmetic(d1, d2, null, Operation.ADD);
	}

	@Override
	public QuantityMeasurementDTO add(QuantityDTO d1, QuantityDTO d2, QuantityDTO target) {
		return executeArithmetic(d1, d2, target, Operation.ADD);
	}

	@Override
	public QuantityMeasurementDTO subtract(QuantityDTO d1, QuantityDTO d2) {
		return executeArithmetic(d1, d2, null, Operation.SUBTRACT);
	}

	@Override
	public QuantityMeasurementDTO subtract(QuantityDTO d1, QuantityDTO d2, QuantityDTO target) {
		return executeArithmetic(d1, d2, target, Operation.SUBTRACT);
	}

	@Override
	public QuantityMeasurementDTO divide(QuantityDTO d1, QuantityDTO d2) {
		return executeArithmetic(d1, d2, null, Operation.DIVIDE);
	}

	@Override
	public List<QuantityMeasurementDTO> getOperationHistory(String operation) {
		return new QuantityMeasurementDTO().fromList(repository.findByOperation(operation.toUpperCase()));
	}

	@Override
	public List<QuantityMeasurementDTO> getMeasurementsByType(String type) {
		return new QuantityMeasurementDTO().fromList(repository.findByThisMeasurementType(type));
	}

	@Override
	public long getOperationCount(String operation) {
		return repository.countByOperationAndIsErrorFalse(operation.toUpperCase());
	}

	@Override
	public List<QuantityMeasurementDTO> getErrorHistory() {
		return new QuantityMeasurementDTO().fromList(repository.findByIsErrorTrue());
	}

	// --- Helper Logic ---

	private QuantityModel<IMeasurable> mapToModel(QuantityDTO dto) {
		if (dto == null)
			throw new QuantityMeasurementException("Quantity data cannot be null");

		String type = dto.measurementType;
		String unitName = dto.unit;
		IMeasurable unit;
		try {
			switch (type) {
			case "LengthUnit":
				unit = LengthUnit.valueOf(unitName.toUpperCase());
				break;
			case "VolumeUnit":
				unit = VolumeUnit.valueOf(unitName.toUpperCase());
				break;
			case "WeightUnit":
				unit = WeightUnit.valueOf(unitName.toUpperCase());
				break;
			case "TemperatureUnit":
				unit = TemperatureUnit.valueOf(unitName.toUpperCase());
				break;
			default:
				throw new QuantityMeasurementException("Invalid Category: " + type);
			}
		} catch (IllegalArgumentException e) {
			throw new InvalidUnitException("Unit '" + unitName + "' is not valid for " + type);
		}
		return new QuantityModel<>(dto.value, unit);
	}

	private void validateModels(QuantityModel<?> m1, QuantityModel<?> m2) {
		if (m1.getUnit().getClass() != m2.getUnit().getClass()) {
			throw new CategoryMismatchException("Incompatible types: " + m1.getUnit().getClass().getSimpleName());
		}
	}

	private QuantityMeasurementDTO executeArithmetic(QuantityDTO d1, QuantityDTO d2, QuantityDTO target,
			Operation opType) {
		QuantityModel<IMeasurable> m1 = mapToModel(d1);
		QuantityModel<IMeasurable> m2 = mapToModel(d2);
		QuantityModel<IMeasurable> mT = (target != null) ? mapToModel(target) : null;

		validateModels(m1, m2);

		Quantity<IMeasurable> q1 = new Quantity<>(m1.getValue(), m1.getUnit());
		Quantity<IMeasurable> q2 = new Quantity<>(m2.getValue(), m2.getUnit());

		Quantity<IMeasurable> result;
		if (opType == Operation.ADD) {
			result = (mT != null) ? q1.add(q2, mT.getUnit()) : q1.add(q2);
		} else if (opType == Operation.SUBTRACT) {
			result = (mT != null) ? q1.subtract(q2, mT.getUnit()) : q1.subtract(q2);
		} else {
			double divResult = q1.divide(q2);
			result = new Quantity<IMeasurable>(divResult, m1.getUnit());
		}

		// Final Persistence with all 13 fields (Formatting updated for resultUnit)
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(d1.value, d1.unit.toUpperCase(),
				d1.measurementType, d2.value, d2.unit.toUpperCase(), d2.measurementType, opType.name(),
				result.getValue(), result.getUnit().getUnitName(), // Clean unit name like "INCHES"
				d1.measurementType, "Success", false, "null");

		repository.save(entity);
		return new QuantityMeasurementDTO().from(entity);
	}
}