package com.nikos.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nikos.Config;
import com.nikos.dto.TopicDTO;
import com.nikos.exceptions.NotFoundException;
import com.nikos.exceptions.ValidationException;
import com.nikos.helper.MediaTypeConstants;
import com.nikos.helper.Response;
import com.nikos.services.TopicService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = Config.TOPICS_CONTROLLER_PREFIX)
@Api(value = "Topics Controller", description = "REST API for Topics Controller", tags = { "TopicsController" })
public class TopicController {

	@Autowired
	TopicService topicService;

	@ApiOperation(value = "Add new topic", tags = { "TopicsController" })
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaTypeConstants.APPLICATION_JSON_UTF8_VALUE)
	public TopicDTO add(@RequestBody TopicDTO topic) {
		if (topic.getId() != null) {
			throw new ValidationException("Inconsistent data. Cannot define auto-generated value.");
		}
		try {
			return topicService.addTopic(topic);
		} catch (Exception e) {
			throw new ValidationException("Could not add topic");
		}
	}

	@ApiResponses({
			@ApiResponse(code = 200, message = "<ol>"
					+ "<li><span style='color:green'>Operation finished successfully</span></li>"
					+ "</ol>", response = TopicDTO.class),
			@ApiResponse(code = 400, message = "<ol>"
					+ "<li><b>Reason:</b><span style='color:red'>Error reason1</span><br/><b>Message:</b><span style='color:blue'>Error message1</span></li>"
					+ "<li><b>Reason:</b><span style='color:red'>Error reason2</span><br/><b>Message:</b><span style='color:blue'>Error message2</span></li>"
					+ "</ol>")
	})
	@ApiOperation(value = "Get All Topics", tags = { "TopicsController" }, notes = "List all available Topics" + "<br/> <b>@return</b> List<TopicDTO>")
	@RequestMapping(method = RequestMethod.GET, value = "/list", produces = MediaTypeConstants.APPLICATION_JSON_UTF8_VALUE)
	public List<TopicDTO> getAllTopics() {
		return topicService.getAllTopics();
	}

	@ApiOperation(value = "Update topic", tags = { "TopicsController" })
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaTypeConstants.APPLICATION_JSON_UTF8_VALUE)
	public TopicDTO update(@RequestBody TopicDTO topic) {
		if (topic.getId() == null) {
			throw new ValidationException("Inconsistent data. No identifier provided.");
		}
		return topicService.updateTopic(topic);
	}

	@ApiOperation(value = "Delete topic", tags = { "TopicsController" })
	@RequestMapping(method = RequestMethod.POST, value = "/delete/{id}")
	public void delete(@PathVariable("id") Long id) {
		topicService.deleteTopic(id);
	}

	@ApiOperation(value = "Get Topic", tags = { "TopicsController" })
	// @RequestMapping(method = RequestMethod.GET)
	@GetMapping(value = "/list/{id}", produces = MediaTypeConstants.APPLICATION_JSON_UTF8_VALUE)
	public TopicDTO get(@PathVariable("id") Long id) {
		return topicService.getTopic(id);
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
