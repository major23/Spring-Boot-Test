package com.nikos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nikos.Config;
import com.nikos.dto.TopicDTO;
import com.nikos.services.TopicService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = Config.TOPICS_CONTROLLER_PREFIX)
@Api(value = "Topics Controller", description = "REST API for Topics Controller", tags = { "TopicsController" })
public class TopicController {

	@Autowired
	private TopicService topicService;

	@ApiOperation(value = "Get All Topics", tags = { "TopicsController" })
	@RequestMapping(method = RequestMethod.GET, value = "/list")
	public List<TopicDTO> getAllTopics() {
		return topicService.getAllTopics();
	}

	@ApiOperation(value = "Get Topic", tags = { "TopicsController" })
	@RequestMapping(method = RequestMethod.GET, value = "/list/{id}")
	public TopicDTO get(@PathVariable("id") String id) {
		return topicService.getTopic(id);
	}

	@ApiOperation(value = "Add new topic", tags = { "TopicsController" })
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public void add(@RequestBody TopicDTO topic) {
		topicService.addTopic(topic);
	}

	@ApiOperation(value = "Update topic", tags = { "TopicsController" })
	@RequestMapping(method = RequestMethod.POST, value = "/update")
	public void update(@RequestBody TopicDTO topic) {
		topicService.updateTopic(topic);
	}

	@ApiOperation(value = "Delete topic", tags = { "TopicsController" })
	@RequestMapping(method = RequestMethod.POST, value = "/delete/{id}")
	public void delete(@PathVariable("id") String id) {
		topicService.deleteTopic(id);
		;
	}

}
