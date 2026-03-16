package com.app.quantitymeasurement.exception;

public class DatabaseException extends QuantityMeasurementException {

	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public static DatabaseException queryFailed(String query, Throwable cause) {
		return new DatabaseException("Query execution failed during: " + query, cause);
	}
}