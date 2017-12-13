package com.nikos.exceptions;

public class TopicValidationException extends RuntimeException {

	private static final long serialVersionUID = 7278573120329585925L;

	public TopicValidationException(String message) {
		super(message);
	}

}
