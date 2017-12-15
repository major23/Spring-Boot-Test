package com.nikos.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nikos.TotalCountersDTO;
import com.nikos.dto.UserDTO;
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
	public void addUser(UserDTO user) {
		user.setId(totalCounters.getTopicId().incrementAndGet());
		userRepository.save(user);
	}

}
