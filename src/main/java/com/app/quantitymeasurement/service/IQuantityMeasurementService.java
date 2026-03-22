package com.app.quantitymeasurement.service;

import java.util.List;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.model.QuantityDTO;

/**
 * Service Interface for Quantity Measurement Operations.
 * Defines the contract for all unit conversions, arithmetic, and history tracking.
 */
public interface IQuantityMeasurementService {

    QuantityMeasurementDTO compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
    
    QuantityMeasurementDTO convert(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
    
    QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
    
    QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO);
    
    QuantityMeasurementDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
    
    QuantityMeasurementDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO);
    
    QuantityMeasurementDTO divide(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
    
    /**
     * Retrieve the history of quantity measurement operations for a specific operation type.
     * @param operation the type of operation (e.g., "ADD", "COMPARE", "CONVERT")
     * @return a list of DTOs representing the operation history
     */
    List<QuantityMeasurementDTO> getOperationHistory(String operation);

    /**
     * Retrieve history based on measurement category.
     * @param type the measurement type (e.g., "LengthUnit", "VolumeUnit")
     * @return a list of DTOs for that specific category
     */
    List<QuantityMeasurementDTO> getMeasurementsByType(String type);
    
    /**
     * Get the count of successful operations for a specific type.
     * @param operation the type of operation
     * @return the total count of operations
     */
    long getOperationCount(String operation);
    
    /**
     * Retrieve all operations that resulted in an error (e.g., Incompatible Units).
     * @return a list of failed operations
     */
    List<QuantityMeasurementDTO> getErrorHistory();
}