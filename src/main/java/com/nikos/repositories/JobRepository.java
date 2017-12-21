package com.nikos.repositories;

import org.springframework.data.repository.CrudRepository;

import com.nikos.dto.JobDTO;

public interface JobRepository extends CrudRepository<JobDTO, Long> {

}
