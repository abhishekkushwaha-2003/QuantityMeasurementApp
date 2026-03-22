package com.app.quantitymeasurement.quantity;

import java.util.function.DoubleBinaryOperator;
import com.app.quantitymeasurement.unit.IMeasurable;

/**
 * Core Logic Class: Handles all arithmetic and conversion operations
 * between quantities using Generics.
 */
public class Quantity<U extends IMeasurable> {
    private double value;
    public U unit;
    
    public Quantity(double value, U unit) {
        if(Double.isNaN(value)) {
            throw new IllegalArgumentException("NaN value provided!");
        }
        if(unit == null) {
            throw new IllegalArgumentException("Unit cannot be null!");            
        }
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }
    
    public double convertTo(U targetUnit) {
        // Convert current value to base unit first
        double baseUnitValue = this.unit.convertToBaseUnit(value);
        // Then convert from base unit to the target unit
        return targetUnit.convertFromBaseUnit(baseUnitValue);
    }
    
    // --- Addition Methods ---
    public Quantity<U> add(Quantity<U> other){
        validateArithmeticOperands(other, null, false);
        double baseResult = performBaseArithmetic(other, ArithmeticOperation.ADD);
        double finalResult = this.unit.convertFromBaseUnit(baseResult);
        return new Quantity<>(finalResult, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit){
        validateArithmeticOperands(other, targetUnit, true);
        double baseResult = performBaseArithmetic(other, ArithmeticOperation.ADD);
        double finalResult = targetUnit.convertFromBaseUnit(baseResult);
        return new Quantity<>(finalResult, targetUnit);
    }
    
    // --- Subtraction Methods ---
    public Quantity<U> subtract(Quantity<U> other){
        validateArithmeticOperands(other, null, false);
        double baseResult = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
        double finalResult = this.unit.convertFromBaseUnit(baseResult);
        return new Quantity<>(finalResult, this.unit);
    }
    
    public Quantity<U> subtract(Quantity<U> other, U targetUnit){
        validateArithmeticOperands(other, targetUnit, true);
        double baseResult = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
        double finalResult = targetUnit.convertFromBaseUnit(baseResult);
        return new Quantity<>(finalResult, targetUnit);
    }
    
    // --- Division Method ---
    public double divide(Quantity<U> other){
        validateArithmeticOperands(other, null, false);
        double baseResult = performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
        // Division usually returns a ratio (double), not a new Quantity with a unit
        return this.unit.convertFromBaseUnit(baseResult);
    }
    
    // --- Equals & Comparison ---
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        
        @SuppressWarnings("unchecked")
        Quantity<U> other = (Quantity<U>) obj;
        if(this.unit.getClass() != other.unit.getClass()) return false;

        // Convert both to Base Unit for an accurate comparison
        double baseValue1 = this.unit.convertToBaseUnit(this.value);
        double baseValue2 = other.unit.convertToBaseUnit(other.value);

        // Delta comparison for double precision
        return Math.abs(baseValue1 - baseValue2) < 0.0001;
    }
    
    @Override
    public String toString() {
        return "Quantity [value=" + value + ", unit=" + unit + "]";
    }

    private void validateArithmeticOperands(Quantity<U> other, U targetUnit, boolean targetUnitRequired) {
        if (other == null)
            throw new IllegalArgumentException("Operand cannot be null");

        if (this.unit.getClass() != other.unit.getClass())
            throw new IllegalArgumentException("Incompatible unit categories (e.g., cannot add Length to Volume)");

        if (!Double.isFinite(this.value) || !Double.isFinite(other.value))
            throw new IllegalArgumentException("Values must be finite numbers");

        if (targetUnitRequired && targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null for this operation");
    }
    
    private double performBaseArithmetic(Quantity<U> other, ArithmeticOperation operation) {
        try {
            this.unit.validateOperationSupport(operation.name());
            other.unit.validateOperationSupport(operation.name());
        } catch(Exception e) {
            throw new UnsupportedOperationException("Operation " + operation.name() + " not supported for this unit type (e.g., Temperature addition).");
        }
        
        double base1 = this.unit.convertToBaseUnit(this.value);
        double base2 = other.unit.convertToBaseUnit(other.value);
        return operation.compute(base1, base2);
    }

    // Internal Enum to manage math operations using Lambda expressions
    private enum ArithmeticOperation {
        ADD((a, b) -> a + b),
        SUBTRACT((a, b) -> a - b),
        DIVIDE((a, b) -> {
            if(b == 0) throw new ArithmeticException("Cannot divide by zero");
            return a / b;
        });
        
        private final DoubleBinaryOperator op;
        
        ArithmeticOperation(DoubleBinaryOperator op) {
            this.op = op;
        }
        
        public double compute(double v1, double v2) {
            return op.applyAsDouble(v1, v2);
        }
    }
}