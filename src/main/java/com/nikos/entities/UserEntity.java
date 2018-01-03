package com.nikos.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "USERS")
public class UserEntity implements Serializable {

	private static final long serialVersionUID = 1157474042893983133L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "SURNAME", nullable = false)
	private String lastName;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "JOB_ID")
	private JobEntity job;

	public UserEntity() {

	}

	public UserEntity(Long id, String name, String lastName, JobEntity job) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.job = job;
	}

	public UserEntity(Long id, String name, String lastName) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
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

	public JobEntity getJob() {
		return job;
	}

	public void setJob(JobEntity job) {
		this.job = job;
	}
}
