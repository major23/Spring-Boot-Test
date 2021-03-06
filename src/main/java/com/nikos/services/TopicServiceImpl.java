package com.nikos.services;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nikos.TotalCountersDTO;
import com.nikos.dto.TopicDTO;
import com.nikos.exceptions.NotFoundException;
import com.nikos.exceptions.ValidationException;
import com.nikos.h2DB.DatabaseHelper;
import com.nikos.helper.ConfigurationParamsDTO;

@Component
public class TopicServiceImpl implements TopicService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TopicServiceImpl.class);

	@Autowired
	private ConfigurationParamsDTO configurationParamsDTO;

	@Autowired
	private TotalCountersDTO totalCountersDTO;

	public List<TopicDTO> getAllTopics() {
		try {
			DatabaseHelper database = new DatabaseHelper(configurationParamsDTO);
			return database.getDatabaseTopics().selectAll();
		} catch (SQLException e) {
			LOGGER.error("Fail to get DB results");
			throw new NotFoundException("Could not get Topics");
		}
	}

	public TopicDTO getTopic(Long id) {
		DatabaseHelper database = new DatabaseHelper(configurationParamsDTO);
		try {
			return database.getDatabaseTopics().selectById(id);
		} catch (SQLException e) {
			LOGGER.error("Fail to get DB results");
			e.printStackTrace();
			return null;
		}
	}

	public TopicDTO addTopic(TopicDTO topic) {
		try {
			DatabaseHelper database = new DatabaseHelper(configurationParamsDTO);
			topic.setId(totalCountersDTO.getTopicId().incrementAndGet());
			database.getDatabaseTopics().insertTopic(topic);
			return topic;
		} catch (SQLException sqle) {
			throw new ValidationException("Could not add Topic");
		}

	}

	public TopicDTO updateTopic(TopicDTO topic) {
		try {
			DatabaseHelper database = new DatabaseHelper(configurationParamsDTO);
			if (database.getDatabaseTopics().selectById(topic.getId()) == null) {
				throw new NotFoundException("Topic with id: " + topic.getId() + " not found");
			}
			database.getDatabaseTopics().updateTopic(topic);
			return topic;
		} catch (SQLException sqle) {
			throw new ValidationException("Could not update Topic");
		}
	}

	public void deleteTopic(Long id) {
		try {
			DatabaseHelper database = new DatabaseHelper(configurationParamsDTO);
			if (database.getDatabaseTopics().selectById(id) == null) {
				throw new NotFoundException("Topic with id: " + id + " not found");
			}
			database.getDatabaseTopics().deleteTopic(id);
		} catch (SQLException sqle) {
			throw new ValidationException("Could not delete Topic");
		}
	}

}
