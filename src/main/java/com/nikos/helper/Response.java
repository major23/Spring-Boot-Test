package com.nikos.helper;

import java.io.Serializable;

public class Response implements Serializable {

	private static final long serialVersionUID = -3778015585710202364L;

	private final int result;
	private final String description;

	public Response(int result, String description) {
		this.result = result;
		this.description = description;
	}

	public int getResult() {
		return this.result;
	}

	public String getDescription() {
		return this.description;
	}

}
