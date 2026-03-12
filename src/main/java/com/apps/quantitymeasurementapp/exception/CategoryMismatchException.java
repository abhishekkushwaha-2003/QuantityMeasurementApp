package com.apps.quantitymeasurementapp.exception;

public class CategoryMismatchException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CategoryMismatchException(String msg) {
		super(msg);
	}
}