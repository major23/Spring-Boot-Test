package com.nikos.repositories;

import org.springframework.data.repository.CrudRepository;

import com.nikos.entities.JobEntity;

public interface JobRepository extends CrudRepository<JobEntity, Long> {

}
