package com.app.quantitymeasurement.entity;

public class QuantityMeasurementEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public double thisValue;
	public String thisUnit;
	public String thisMeasurementType;

	public double thatValue;
	public String thatUnit;
	public String thatMeasurementType;

	public String operation;

	public double resultValue;
	public String resultUnit;
	public String resultMeasurementType;

	public String resultString;
	public boolean isError;
	public String errorMessage;

	// Default constructor for JDBC Database Retrieval
	public QuantityMeasurementEntity() {
	}

	// The 10-Parameter Constructor your ServiceImpl is looking for!
	public QuantityMeasurementEntity(double thisValue, String thisUnit, String thisMeasurementType, double thatValue,
			String thatUnit, String thatMeasurementType, String operation, double resultValue, String resultUnit,
			String resultMeasurementType) {
		this.thisValue = thisValue;
		this.thisUnit = thisUnit;
		this.thisMeasurementType = thisMeasurementType;
		this.thatValue = thatValue;
		this.thatUnit = thatUnit;
		this.thatMeasurementType = thatMeasurementType;
		this.operation = operation;
		this.resultValue = resultValue;
		this.resultUnit = resultUnit;
		this.resultMeasurementType = resultMeasurementType;
	}

	public QuantityMeasurementEntity(double thisValue, String thisUnit, String thisMeasurementType, double thatValue,
			String thatUnit, String thatMeasurementType, String operation, String errorMessage, boolean isError) {
		this.thisValue = thisValue;
		this.thisUnit = thisUnit;
		this.thisMeasurementType = thisMeasurementType;
		this.thatValue = thatValue;
		this.thatUnit = thatUnit;
		this.thatMeasurementType = thatMeasurementType;
		this.operation = operation;
		this.errorMessage = errorMessage;
		this.isError = isError;
	}
}