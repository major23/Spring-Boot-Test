package com.nikos.services;

import java.util.List;

import com.nikos.dto.UserDTO;

public interface UserService {

	public List<UserDTO> getAllUsers();

	public UserDTO getUser(Long id);

	public UserDTO addUser(UserDTO user);

	public UserDTO updateUser(UserDTO user);

	public void deleteUser(Long id);

}
