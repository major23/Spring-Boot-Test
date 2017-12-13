package com.nikos.exceptions;

public class TopicNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4569803259616596268L;

	public TopicNotFoundException(String message) {
		super(message);
	}

}
