package com.app.quantitymeasurement.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    private ResponseEntity<ErrorResponse> buildResponse(Exception e, HttpStatus status, String errorTitle, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse();
        error.setDateTime(LocalDateTime.now());
        error.setStatus(status.value());
        error.setError(errorTitle);
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(CategoryMismatchException.class)
    public ResponseEntity<ErrorResponse> handleCategoryMismatch(CategoryMismatchException e, HttpServletRequest request) {
        return buildResponse(e, HttpStatus.BAD_REQUEST, "Category Mismatch Error", request);
    }

    @ExceptionHandler(InvalidUnitException.class)
    public ResponseEntity<ErrorResponse> handleInvalidUnit(InvalidUnitException e, HttpServletRequest request) {
        return buildResponse(e, HttpStatus.NOT_ACCEPTABLE, "Invalid Unit Specified", request);
    }

    @ExceptionHandler(QuantityMeasurementException.class)
    public ResponseEntity<ErrorResponse> handleGeneral(QuantityMeasurementException e, HttpServletRequest request) {
        return buildResponse(e, HttpStatus.BAD_REQUEST, "Measurement Logic Error", request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobal(Exception e, HttpServletRequest request) {
        return buildResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, "Server Error!", request);
    }
}