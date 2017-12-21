package com.nikos.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.nikos.TotalCountersDTO;
import com.nikos.dto.JobDTO;
import com.nikos.exceptions.NotFoundException;
import com.nikos.exceptions.ValidationException;
import com.nikos.repositories.JobRepository;

@Component
public class JobServiceImpl implements JobService {

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	TotalCountersDTO totalCounters;

	@Override
	public List<JobDTO> getAll() {
		List<JobDTO> jobList = new ArrayList<>();
		Iterable<JobDTO> list = jobRepository.findAll();
		for (JobDTO obj : list) {
			jobList.add(obj);
		}
		return jobList;
	}

	@Override
	public JobDTO get(Long id) {
		return jobRepository.findOne(id);
	}

	@Override
	public JobDTO add(JobDTO job) {
		try {
			return jobRepository.save(job);
		} catch (DataIntegrityViolationException e) {
			throw new ValidationException("Constraint Violation: " + e.getMostSpecificCause().getMessage());
		}

	}

	@Override
	public JobDTO update(JobDTO job) {
		JobDTO obj = jobRepository.findOne(job.getId());
		if (obj == null) {
			throw new NotFoundException("Job with id: " + job.getId() + " not found");
		}
		try {
			return jobRepository.save(job);
		} catch (DataIntegrityViolationException e) {
			throw new ValidationException("Constraint Violation: " + e.getMostSpecificCause().getMessage());
		}

	}

	@Override
	public void delete(Long id) {
		JobDTO obj = jobRepository.findOne(id);
		if (obj == null) {
			throw new NotFoundException("Job with id: " + id + " not found");
		}
		jobRepository.delete(id);
	}

}
