package com.nikos.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nikos.Config;
import com.nikos.dto.UserDTO;
import com.nikos.exceptions.NotFoundException;
import com.nikos.exceptions.ValidationException;
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
	@GetMapping(value = "/list")
	public List<UserDTO> listUsers() {
		return userService.getAllUsers();
	}

	@ApiOperation(value = "Add user", tags = { "UsersController" })
	@PostMapping(value = "/add")
	public UserDTO addUser(@RequestBody UserDTO user) {
		if (user.getId() != null) {
			throw new ValidationException("Inconsistent data. Cannot define auto-generated value.");
		}
		return userService.addUser(user);
	}

	@ApiOperation(value = "Test path variable", tags = { "UsersController" })
	@RequestMapping(value = "/{user}", method = RequestMethod.GET)
	public String getUser(@PathVariable("user") Long a, @RequestParam(value = "message", defaultValue = "Hi") String msg) {
		return msg + " user no." + a;
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
