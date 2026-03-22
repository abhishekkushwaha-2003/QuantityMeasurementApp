package com.app.quantitymeasurement.model;
import com.app.quantitymeasurement.unit.IMeasurable;

/**
 * Generic model to wrap a numeric value with its specific measurement unit.
 * Part of the core logic to handle type-safe conversions.
 */
public class QuantityModel<U extends IMeasurable> {
    public double value;
    public U unit;
    
    public QuantityModel(double value, U unit) {
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return "QuantityModel [value=" + value + ", unit=" + unit + "]";
    }
}