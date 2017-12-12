package com.nikos.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "name", "lastName" })
public class UserDTO {

	private String name;
	private String lastName;

	public UserDTO() {

	}

	public UserDTO(String name, String lastName) {
		this.name = name;
		this.lastName = lastName;
	}

	public UserDTO(UserDTO user) {
		this.name = user.getName();
		this.lastName = user.getLastName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
