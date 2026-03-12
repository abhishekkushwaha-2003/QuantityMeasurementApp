package com.apps.quantitymeasurementapp.entity;

import com.apps.quantitymeasurementapp.unit.IMeasurable;

public class QuantityDTO {
	
	public double value;
	public String unit;
	public String measurementType;

	public QuantityDTO(double value, IMeasurable unit) {
		this.value = value;
		this.unit = unit.getUnitName();
		this.measurementType = unit.getMeasurableType();
	}
	public QuantityDTO(double value, String unit, String measurementType) {
		this.value = value;
		this.unit = unit;
		this.measurementType = measurementType;
	}

	public double getValue() {
		return value;
	}

	public String getUnit() {
		return unit;
	}

	public String getMeasurementType() {
		return measurementType.replace("UNIT", "");
	}

	@Override
	public String toString() {
		return "QuantityDTO [value=" + value + ", unit=" + unit + ", measurementType=" + getMeasurementType() + "]";
	}
}