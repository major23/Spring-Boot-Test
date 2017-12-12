package com.nikos.controllers;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nikos.Config;
import com.nikos.Greeting;
import com.nikos.dto.UserDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController

// This will be appended after the port and before all requests in this class
@RequestMapping(value = Config.CONTROLLER1_PREFIX)

// The below will be displayed on Swagger
@Api(value = "Controller1 name", description = "REST API for Controller1", tags = { "Controller1" })
public class Controller1 {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Controller1.class);

	@ApiOperation(value = "Import User", tags = { "Controller1" })
	@RequestMapping(value = "/importUser", method = RequestMethod.POST)
	public ResponseEntity<UserDTO> importUserRequest(@RequestBody UserDTO user, @RequestParam(value = "pass") boolean pass) {
		if (pass) {
			logger.info(user + " imported successfully");
			return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.OK);
		}
		logger.error("Failed to import user " + user);
		return new ResponseEntity<UserDTO>(new UserDTO(), HttpStatus.BAD_REQUEST);
	}

	@ApiOperation(value = "Home API", tags = { "Controller1" })
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "Welcome controller1";
	}

	@ApiOperation(value = "Greeting API", tags = { "Controller1" })
	@RequestMapping(value = "/greeting1", method = RequestMethod.GET)
	public Greeting greeting(@RequestParam(value = "msg") String message,
			@RequestParam(value = "name", defaultValue = "User") String name) {
		return new Greeting(message, name);
	}

	@ApiOperation(value = "Exit API", tags = { "Controller1" })
	@RequestMapping(value = "/exit1", method = RequestMethod.GET)
	public String exit(@RequestParam(value = "name", defaultValue = "User") String name) {
		return "GoodBye " + name;
	}

	@ApiOperation(value = "Test path variable", tags = { "Controller1" })
	@RequestMapping(value = "/{user}", method = RequestMethod.GET)
	public String getUser(@PathVariable("user") Long a, @RequestParam(value = "message", defaultValue = "Hi") String msg) {
		return msg + " user no." + a;
	}

}
