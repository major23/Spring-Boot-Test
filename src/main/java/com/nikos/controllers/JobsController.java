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
import com.nikos.dto.JobDTO;
import com.nikos.exceptions.NotFoundException;
import com.nikos.exceptions.ValidationException;
import com.nikos.helper.MediaTypeConstants;
import com.nikos.helper.Response;
import com.nikos.services.JobService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController

// This will be appended after the port and before all requests in this class
@RequestMapping(value = Config.JOBS_CONTROLLER_PREFIX)

// The below will be displayed on Swagger
@Api(value = "Jobs Controller", description = "REST API for Jobs Controller", tags = { "JobsController" })
public class JobsController {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JobsController.class);

	@Autowired
	JobService jobService;

	@ApiOperation(value = "List Jobs", tags = { "JobsController" })
	@GetMapping(value = "/list", produces = MediaTypeConstants.APPLICATION_JSON_UTF8_VALUE)
	public List<JobDTO> listUsers() {
		return jobService.getAll();
	}

	@ApiOperation(value = "Add Job", tags = { "JobsController" })
	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaTypeConstants.APPLICATION_JSON_UTF8_VALUE)
	public JobDTO addJob(@RequestBody JobDTO job) {
		if (job.getId() != null) {
			throw new ValidationException("Inconsistent data. Cannot define auto-generated value.");
		}
		return jobService.add(job);
	}

	@ApiOperation(value = "List one Job", tags = { "JobsController" })
	@GetMapping(value = "/list/{id}", produces = MediaTypeConstants.APPLICATION_JSON_UTF8_VALUE)
	public JobDTO list(@PathVariable("id") Long id) {
		return jobService.get(id);
	}

	@ApiOperation(value = "Update Job", tags = { "JobsController" })
	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaTypeConstants.APPLICATION_JSON_UTF8_VALUE)
	public JobDTO updateUser(@RequestBody JobDTO job) {
		if (job.getId() == null) {
			throw new ValidationException("Inconsistent data. Id value required");
		}
		return jobService.update(job);
	}

	@ApiOperation(value = "Delete Job", tags = { "JobsController" })
	@PostMapping(value = "/delete")
	public void delete(@RequestParam Long id) {
		jobService.delete(id);

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
