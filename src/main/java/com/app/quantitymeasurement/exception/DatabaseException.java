package com.app.quantitymeasurement.exception;

public class DatabaseException extends QuantityMeasurementException {
    private static final long serialVersionUID = 1L;
    public DatabaseException(String msg) { super(msg); }
    public DatabaseException(String message, Throwable cause) { super(message, cause); }
}