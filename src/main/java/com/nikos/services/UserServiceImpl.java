package com.nikos.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.nikos.TotalCountersDTO;
import com.nikos.dto.UserDTO;
import com.nikos.exceptions.NotFoundException;
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
	public UserDTO getUser(Long id) {
		return userRepository.findOne(id);
	}

	@Override
	public UserDTO addUser(UserDTO user) {
		try {
			return userRepository.save(user);
		} catch (DataIntegrityViolationException e) {
			throw new ValidationException("Constraint Violation: " + e.getMostSpecificCause().getMessage());
		}

	}

	@Override
	public UserDTO updateUser(UserDTO user) {
		UserDTO u = userRepository.findOne(user.getId());
		if (u == null) {
			throw new NotFoundException("User with id: " + user.getId() + " not found");
		}
		try {
			return userRepository.save(user);
		} catch (DataIntegrityViolationException e) {
			throw new ValidationException("Constraint Violation: " + e.getMostSpecificCause().getMessage());
		}

	}

	@Override
	public void deleteUser(Long id) {
		UserDTO u = userRepository.findOne(id);
		if (u == null) {
			throw new NotFoundException("User with id: " + id + " not found");
		}
		userRepository.delete(id);
	}

}
