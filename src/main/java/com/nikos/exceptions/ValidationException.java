package com.nikos.exceptions;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 7278573120329585925L;

	public ValidationException(String message) {
		super(message);
	}

}
