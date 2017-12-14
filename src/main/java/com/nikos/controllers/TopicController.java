package com.nikos.controllers;

import java.io.IOException;
import java.sql.SQLException;
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
import com.nikos.exceptions.TopicNotFoundException;
import com.nikos.exceptions.TopicValidationException;
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

	@ApiResponses({
			@ApiResponse(code = 200, message = "<ol>"
					+ "<li><span style='color:green'>Operation finished successfully</span></li>"
					+ "</ol>"),
			@ApiResponse(code = 400, message = "<ol>"
					+ "<li><b>Reason:</b><span style='color:red'>Error reason1</span><br/><b>Message:</b><span style='color:blue'>Error message1</span></li>"
					+ "<li><b>Reason:</b><span style='color:red'>Error reason2</span><br/><b>Message:</b><span style='color:blue'>Error message2</span></li>"
					+ "</ol>")
	})
	@ApiOperation(value = "Get All Topics", tags = { "TopicsController" }, notes = "List all available Topics" + "<br/> <b>@return</b> List<TopicDTO>")
	@RequestMapping(method = RequestMethod.GET, value = "/list")
	public List<TopicDTO> getAllTopics() {
		return topicService.getAllTopics();
	}

	@ApiOperation(value = "Get Topic", tags = { "TopicsController" })
	// @RequestMapping(method = RequestMethod.GET)
	@GetMapping(value = "/list/{id}")
	public TopicDTO get(@PathVariable("id") Long id) {
		return topicService.getTopic(id);
	}

	@ApiOperation(value = "Add new topic", tags = { "TopicsController" })
	@RequestMapping(method = RequestMethod.POST, value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaTypeConstants.APPLICATION_JSON_UTF8_VALUE)
	public TopicDTO add(@RequestBody TopicDTO topic) {
		if (topic.getId() != null) {
			throw new TopicValidationException("Inconsistent data. Cannot define auto-generated value.");
		}
		try {
			return topicService.addTopic(topic);
		} catch (Exception e) {
			throw new TopicValidationException("Could not add topic");
		}
	}

	@ApiOperation(value = "Update topic", tags = { "TopicsController" })
	@RequestMapping(method = RequestMethod.POST, value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaTypeConstants.APPLICATION_JSON_UTF8_VALUE)
	public TopicDTO update(@RequestBody TopicDTO topic) {
		if (topic.getId() == null) {
			throw new TopicValidationException("Inconsistent data. No identifier provided.");
		}
		try {
			return topicService.updateTopic(topic);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	@ApiOperation(value = "Delete topic", tags = { "TopicsController" })
	@RequestMapping(method = RequestMethod.POST, value = "/delete/{id}")
	public void delete(@PathVariable("id") String id) {
		topicService.deleteTopic(id);
	}

	@ExceptionHandler(TopicNotFoundException.class)
	public Response notFound(Exception e, HttpServletResponse response) throws IOException {
		return new Response(HttpStatus.NOT_FOUND.value(), e.getMessage());
	}

	@ExceptionHandler(TopicValidationException.class)
	public Response invalid(Exception e, HttpServletResponse response) throws IOException {
		return new Response(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	}

}
