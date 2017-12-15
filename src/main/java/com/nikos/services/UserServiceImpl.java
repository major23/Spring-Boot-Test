package com.nikos.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.nikos.TotalCountersDTO;
import com.nikos.dto.UserDTO;
import com.nikos.exceptions.ValidationException;
import com.nikos.repositories.UserRepository;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	TotalCountersDTO totalCounters;

	@Override
	public List<UserDTO> getAllUsers() {
		List<UserDTO> userList = new ArrayList<>();
		Iterable<UserDTO> list = userRepository.findAll();
		for (UserDTO u : list) {
			userList.add(u);
		}
		return userList;
	}

	@Override
	public UserDTO addUser(UserDTO user) {
		try {
			return userRepository.save(user);
		} catch (DataIntegrityViolationException e) {
			throw new ValidationException("Constraint Violation: " + e.getMostSpecificCause().getMessage());
		}

	}

}
