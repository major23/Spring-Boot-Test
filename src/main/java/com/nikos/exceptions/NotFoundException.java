package com.nikos.exceptions;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4569803259616596268L;

	public NotFoundException(String message) {
		super(message);
	}

}
