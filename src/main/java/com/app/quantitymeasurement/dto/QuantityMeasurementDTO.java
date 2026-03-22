package com.app.quantitymeasurement.dto;

import java.util.ArrayList;
import java.util.List;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class QuantityMeasurementDTO {
    public double thisValue;
    public String thisUnit;
    public String thisMeasurementType;
    public double thatValue;
    public String thatUnit;
    public String thatMeasurementType;
    public String operation;
    public double resultValue;
    public String resultUnit;
    public String resultString;
    public String resultMeasurementType;
    
    @JsonProperty("error")
    public boolean error;
    public String errorMessage;

    // Default Constructor
    public QuantityMeasurementDTO() {}

    // Full Constructor
    public QuantityMeasurementDTO(double thisValue, String thisUnit, String thisMeasurementType, double thatValue,
            String thatUnit, String thatMeasurementType, String operation, double resultValue, String resultUnit,
            String resultString, String resultMeasurementType, boolean error, String errorMessage) {
        this.thisValue = thisValue;
        this.thisUnit = thisUnit;
        this.thisMeasurementType = thisMeasurementType;
        this.thatValue = thatValue;
        this.thatUnit = thatUnit;
        this.thatMeasurementType = thatMeasurementType;
        this.operation = operation;
        this.resultValue = resultValue;
        this.resultUnit = resultUnit;
        this.resultString = resultString;
        this.resultMeasurementType = resultMeasurementType;
        this.error = error;
        this.errorMessage = errorMessage;
    }

    public QuantityMeasurementDTO from(QuantityMeasurementEntity entity) {
        return new QuantityMeasurementDTO(
            entity.getThisValue(), entity.getThisUnit(), entity.getThisMeasurementType(), 
            entity.getThatValue(), entity.getThatUnit(), entity.getThatMeasurementType(), 
            entity.getOperation(), entity.getResultValue(), entity.getResultUnit(), 
            entity.getResultString(), entity.getResultMeasurementType(),  
            entity.isError(), entity.getErrorMessage()
        );
    }
    
    public QuantityMeasurementEntity toEntity() {
        return new QuantityMeasurementEntity(
            thisValue, thisUnit, thisMeasurementType, 
            thatValue, thatUnit, thatMeasurementType, 
            operation, resultValue, resultUnit, 
            resultMeasurementType, resultString, 
            error, errorMessage
        );
    }
    
    public List<QuantityMeasurementDTO> fromList(List<QuantityMeasurementEntity> list){         
        List<QuantityMeasurementDTO> compute = new ArrayList<>();
        if (list != null) {
            for(QuantityMeasurementEntity entity : list) {
                compute.add(from(entity));
            }
        }
        return compute;
    }

    // manual getters
    public boolean isError() { return error; }
    public void setError(boolean error) { this.error = error; }
}