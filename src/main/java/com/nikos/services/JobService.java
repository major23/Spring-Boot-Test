package com.nikos.services;

import java.util.List;

import com.nikos.dto.JobDTO;

public interface JobService {

	public List<JobDTO> getAll();

	public JobDTO get(Long id);

	public JobDTO add(JobDTO user);

	public JobDTO update(JobDTO user);

	public void delete(Long id);

}
