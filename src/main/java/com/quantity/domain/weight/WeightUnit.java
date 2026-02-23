package com.quantity.domain.weight;

public enum WeightUnit {

    KILOGRAM(1.0),        
    GRAM(0.001),          
    POUND(0.453592);     
    private final double conversionFactorToKg;

    WeightUnit(double conversionFactorToKg) {
        this.conversionFactorToKg = conversionFactorToKg;
    }

    public double getConversionFactor() {
        return conversionFactorToKg;
    }
    public double convertToBaseUnit(double value) {

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid numeric value");

        return value * conversionFactorToKg;
    }

    public double convertFromBaseUnit(double baseValue) {

        if(!Double.isFinite(baseValue))
            throw new IllegalArgumentException("Invalid numeric value");

        return baseValue / conversionFactorToKg;
    }
}