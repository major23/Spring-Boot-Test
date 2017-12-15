package com.nikos.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1157474042893983133L;

	@Id
	private Long id;
	private String name;
	private String lastName;

	public UserDTO() {

	}

	public UserDTO(Long id, String name, String lastName) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
	}

	public UserDTO(UserDTO user) {
		this.id = user.getId();
		this.name = user.getName();
		this.lastName = user.getLastName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
