package com.nikos.dto;

import com.nikos.entities.UserEntity;

public class UserDTO implements BaseManagementDTO<UserEntity, Long> {

	private static final long serialVersionUID = 6379096485594109832L;

	private Long id;
	private String name;
	private String lastName;
	private Long jobId;

	public UserDTO() {

	}

	public UserDTO(Long id, String name, String lastName, Long jobId) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.jobId = jobId;
	}

	public UserDTO(UserDTO user) {
		this.id = user.getId();
		this.name = user.getName();
		this.lastName = user.getLastName();
		this.jobId = user.getJobId();
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

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public UserEntity toEntity() {
		return new UserEntity(this.getId(), this.getName(), this.getLastName());
	}

	public UserDTO fromEntity(UserEntity entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.lastName = entity.getLastName();
		this.jobId = entity.getJob().getId();
		return this;
	}

}
