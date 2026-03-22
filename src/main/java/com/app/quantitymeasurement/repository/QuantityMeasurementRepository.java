package com.app.quantitymeasurement.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.quantitymeasurement.model.QuantityMeasurementEntity;

/**
 * UC 17: Repository layer for performing database operations 
 * on Quantity Measurement history.
 */
@Repository
public interface QuantityMeasurementRepository extends JpaRepository<QuantityMeasurementEntity, Long> {

    // Find all measurements by operation type (ADD, COMPARE, etc.)
    List<QuantityMeasurementEntity> findByOperation(String operation);

    // Find all measurements by measurement type (LengthUnit, VolumeUnit, etc.)
    List<QuantityMeasurementEntity> findByThisMeasurementType(String measurementType);

    // Find measurements created after a specific date/time
    List<QuantityMeasurementEntity> findByCreatedAtAfter(LocalDateTime date);

    // Custom JPQL query to filter only successful operations
    @Query("SELECT e FROM QuantityMeasurementEntity e WHERE e.operation = :operation AND e.isError = false")
    List<QuantityMeasurementEntity> findSuccessfulOperations(@Param("operation") String operation);
    
    // Count total successful operations for a specific type
    long countByOperationAndIsErrorFalse(String operation);

    // Retrieve only those operations that resulted in an error
    List<QuantityMeasurementEntity> findByIsErrorTrue();
}