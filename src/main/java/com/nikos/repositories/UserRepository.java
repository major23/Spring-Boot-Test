package com.nikos.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nikos.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

	public List<UserEntity> findByJobId(Long id);

}
