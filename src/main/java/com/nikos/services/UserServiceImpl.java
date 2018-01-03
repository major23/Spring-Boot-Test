package com.nikos.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nikos.TotalCountersDTO;
import com.nikos.dto.UserDTO;
import com.nikos.entities.JobEntity;
import com.nikos.entities.UserEntity;
import com.nikos.exceptions.NotFoundException;
import com.nikos.repositories.JobRepository;
import com.nikos.repositories.UserRepository;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	TotalCountersDTO totalCounters;

	@Override
	public List<UserDTO> getAllUsers() {
		List<UserDTO> userList = new ArrayList<>();
		Iterable<UserEntity> list = userRepository.findAll();
		for (UserEntity u : list) {
			userList.add(new UserDTO().fromEntity(u));
		}
		return userList;
	}

	@Override
	public UserDTO getUser(Long id) {
		return new UserDTO().fromEntity(userRepository.findOne(id));
	}

	@Override
	public UserDTO addUser(UserDTO user) {
		UserEntity userEnt = user.toEntity();
		JobEntity jobEnt = jobRepository.findOne(user.getJobId());

		if (jobEnt == null) {
			throw new NotFoundException("Job with id: " + user.getJobId() + " not found");
		}

		userEnt.setJob(jobEnt);
		return new UserDTO().fromEntity(userRepository.save(userEnt));
	}

	@Override
	public UserDTO updateUser(UserDTO user) {
		UserEntity userEntity = userRepository.findOne(user.getId());
		if (userEntity == null) {
			throw new NotFoundException("User with id: " + user.getId() + " not found");
		}

		UserEntity updUser = user.toEntity();
		JobEntity jobEnt = jobRepository.findOne(user.getJobId());
		if (jobEnt == null) {
			throw new NotFoundException("Job with id: " + user.getJobId() + " not found");
		}

		updUser.setJob(jobEnt);
		return new UserDTO().fromEntity(userRepository.save(updUser));
	}

	@Override
	public void deleteUser(Long id) {
		UserEntity u = userRepository.findOne(id);
		if (u == null) {
			throw new NotFoundException("User with id: " + id + " not found");
		}
		userRepository.delete(id);
	}

}
