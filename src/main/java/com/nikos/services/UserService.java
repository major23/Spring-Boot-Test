package com.nikos.services;

import java.util.List;

import com.nikos.dto.UserDTO;

public interface UserService {

	public List<UserDTO> getAllUsers();

	public UserDTO addUser(UserDTO user);

}
