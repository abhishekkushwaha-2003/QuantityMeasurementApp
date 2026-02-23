package com.quantity.domain.length;

public enum LengthUnit {

    FEET(1.0),                
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0/30.48);

    private final double conversionFactorToFeet;

    LengthUnit(double conversionFactorToFeet) {
        this.conversionFactorToFeet = conversionFactorToFeet;
    }

    public double getConversionFactor() {
        return conversionFactorToFeet;
    }

    public double convertToBaseUnit(double value) {

        if(!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid numeric value");

        return value * conversionFactorToFeet;
    }
    public double convertFromBaseUnit(double baseValue) {

        if(!Double.isFinite(baseValue))
            throw new IllegalArgumentException("Invalid numeric value");

        return baseValue / conversionFactorToFeet;
    }
}