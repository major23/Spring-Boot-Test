package com.nikos.repositories;

import org.springframework.data.repository.CrudRepository;

import com.nikos.dto.UserDTO;

public interface UserRepository extends CrudRepository<UserDTO, Long> {

}
