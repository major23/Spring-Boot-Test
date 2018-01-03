package com.nikos.dto;

import com.nikos.entities.JobEntity;

public class JobDTO implements BaseManagementDTO<JobEntity, Long> {

	private static final long serialVersionUID = -6045481849803591190L;

	private Long id;
	private String title;
	private String description;

	public JobDTO() {

	}

	public JobDTO(Long id, String title, String description) {
		this.id = id;
		this.title = title;
		this.description = description;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public JobEntity toEntity() {
		return new JobEntity(this.getId(), this.getTitle(), this.getDescription());
	}

	@Override
	public JobDTO fromEntity(JobEntity entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.description = entity.getDescription();
		return this;
	}

}
