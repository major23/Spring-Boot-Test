package com.nikos.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nikos.Config;
import com.nikos.dto.UserDTO;
import com.nikos.exceptions.NotFoundException;
import com.nikos.exceptions.ValidationException;
import com.nikos.helper.MediaTypeConstants;
import com.nikos.helper.Response;
import com.nikos.services.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController

// This will be appended after the port and before all requests in this class
@RequestMapping(value = Config.USERS_CONTROLLER_PREFIX)

// The below will be displayed on Swagger
@Api(value = "Users Controller", description = "REST API for Users Controller", tags = { "UsersController" })
public class UsersController {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UsersController.class);

	@Autowired
	UserService userService;

	@ApiOperation(value = "List Users", tags = { "UsersController" })
	@GetMapping(value = "/list", produces = MediaTypeConstants.APPLICATION_JSON_UTF8_VALUE)
	public List<UserDTO> listUsers() {
		return userService.getAllUsers();
	}

	@ApiOperation(value = "Add user", tags = { "UsersController" })
	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaTypeConstants.APPLICATION_JSON_UTF8_VALUE)
	public UserDTO addUser(@RequestBody UserDTO user) {
		if (user.getId() != null) {
			throw new ValidationException("Inconsistent data. Cannot define auto-generated value.");
		}
		return userService.addUser(user);
	}

	@ApiOperation(value = "List One User", tags = { "UsersController" })
	@GetMapping(value = "/list/{id}", produces = MediaTypeConstants.APPLICATION_JSON_UTF8_VALUE)
	public UserDTO listUser(@PathVariable("id") Long id) {
		return userService.getUser(id);
	}

	@ApiOperation(value = "Update User", tags = { "UsersController" })
	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaTypeConstants.APPLICATION_JSON_UTF8_VALUE)
	public UserDTO updateUser(@RequestBody UserDTO user) {
		if (user.getId() == null) {
			throw new ValidationException("Inconsistent data. Id value required");
		}
		return userService.updateUser(user);
	}

	@ApiOperation(value = "Delete User", tags = { "UsersController" })
	@PostMapping(value = "/delete")
	public void deleteUser(@RequestParam Long id) {
		userService.deleteUser(id);

	}

	@ExceptionHandler(NotFoundException.class)
	public Response notFound(Exception e, HttpServletResponse response) throws IOException {
		return new Response(HttpStatus.NOT_FOUND.value(), e.getMessage());
	}

	@ExceptionHandler(ValidationException.class)
	public Response invalid(Exception e, HttpServletResponse response) throws IOException {
		return new Response(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	}

}
