package com.apps.quantitymeasurementapp.entity;

import java.util.Objects;

public class QuantityMeasurementEntity implements java.io.Serializable{


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
	
	//For comparison Equals or not
	public String resultString;
	
	//Error handling
	public boolean isError;
	public String errorMessage;
	

    public QuantityMeasurementEntity(double thisValue, String thisUnit, String thisMeasurementType, double thatValue, String thatUnit, String thatMeasurementType, String operation, String resultString) {
        initializeCommonFields(thisValue, thisUnit, thisMeasurementType, thatValue, thatUnit, thatMeasurementType, operation);

        this.resultString = resultString;
        this.isError = false;
    }


  
    public QuantityMeasurementEntity(double thisValue, String thisUnit, String thisMeasurementType, double thatValue, String thatUnit, String thatMeasurementType, String operation, double resultValue, String resultUnit, String resultMeasurementType) {
        initializeCommonFields(thisValue, thisUnit, thisMeasurementType, thatValue, thatUnit, thatMeasurementType, operation);

        this.resultValue = resultValue;
        this.resultUnit = resultUnit;
        this.resultMeasurementType = resultMeasurementType;
        this.isError = false;
    }


    public QuantityMeasurementEntity(double thisValue, String thisUnit, String thisMeasurementType, double thatValue, String thatUnit, String thatMeasurementType, String operation, double resultValue) {
        initializeCommonFields(thisValue, thisUnit, thisMeasurementType, thatValue, thatUnit, thatMeasurementType, operation);

        this.resultValue = resultValue;
        this.isError = false;
    }


    public QuantityMeasurementEntity(double thisValue, String thisUnit, String thisMeasurementType, double thatValue, String thatUnit, String thatMeasurementType, String operation, String errorMessage, boolean isError) {
        initializeCommonFields(thisValue, thisUnit, thisMeasurementType, thatValue, thatUnit, thatMeasurementType, operation);

        this.errorMessage = errorMessage;
        this.isError = isError;
    }


  
    private void initializeCommonFields(double thisValue, String thisUnit, String thisMeasurementType, double thatValue, String thatUnit, String thatMeasurementType, String operation) {
        this.thisValue = thisValue;
        this.thisUnit = thisUnit;
        this.thisMeasurementType = thisMeasurementType;

        this.thatValue = thatValue;
        this.thatUnit = thatUnit;
        this.thatMeasurementType = thatMeasurementType;

        this.operation = operation;
    }
    
    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        QuantityMeasurementEntity that = (QuantityMeasurementEntity) obj;
        return Double.compare(that.thisValue, thisValue) == 0 &&
                Double.compare(that.thatValue, thatValue) == 0 &&
                Double.compare(that.resultValue, resultValue) == 0 &&
                isError == that.isError &&
                Objects.equals(thisUnit, that.thisUnit) &&
                Objects.equals(thisMeasurementType, that.thisMeasurementType) &&
                Objects.equals(thatUnit, that.thatUnit) &&
                Objects.equals(thatMeasurementType, that.thatMeasurementType) &&
                Objects.equals(operation, that.operation) &&
                Objects.equals(resultUnit, that.resultUnit) &&
                Objects.equals(resultMeasurementType, that.resultMeasurementType) &&
                Objects.equals(resultString, that.resultString) &&
                Objects.equals(errorMessage, that.errorMessage);
    }
    
    @Override
    public String toString() {

        return "QuantityMeasurementEntity Details:\n" +
                "----------------------------------\n" +
                "This Value            : " + thisValue + "\n" +
                "This Unit             : " + thisUnit + "\n" +
                "This Measurement Type : " + thisMeasurementType + "\n" +
                "\n" +
                "That Value            : " + thatValue + "\n" +
                "That Unit             : " + thatUnit + "\n" +
                "That Measurement Type : " + thatMeasurementType + "\n" +
                "\n" +
                "Operation             : " + operation + "\n" +
                "\n" +
                "Result Value          : " + resultValue + "\n" +
                "Result Unit           : " + resultUnit + "\n" +
                "Result MeasurementType: " + resultMeasurementType + "\n" +
                "Result String         : " + resultString + "\n" +
                "\n" +
                "Error                 : " + isError + "\n" +
                "Error Message         : " + errorMessage + "\n";
    }    
}