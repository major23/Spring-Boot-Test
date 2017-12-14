package com.nikos.services;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nikos.TotalCountersDTO;
import com.nikos.dto.TopicDTO;
import com.nikos.exceptions.TopicNotFoundException;
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
		DatabaseHelper database = new DatabaseHelper(configurationParamsDTO);
		try {
			return database.getDatabaseTopics().selectAll();
		} catch (SQLException e) {
			LOGGER.error("Fail to get DB results");
			e.printStackTrace();
			return null;
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

	public TopicDTO addTopic(TopicDTO topic) throws SQLException {
		DatabaseHelper database = new DatabaseHelper(configurationParamsDTO);
		topic.setId(totalCountersDTO.getTopicId().incrementAndGet());
		database.getDatabaseTopics().insertTopic(topic);
		return topic;
	}

	public TopicDTO updateTopic(TopicDTO topic) throws SQLException {
		DatabaseHelper database = new DatabaseHelper(configurationParamsDTO);
		if (database.getDatabaseTopics().selectById(topic.getId()) == null) {
			throw new TopicNotFoundException("Topic with id: " + topic.getId() + " not found");
		}
		database.getDatabaseTopics().updateTopic(topic);
		return topic;
	}

	public void deleteTopic(String id) {
		// for (TopicDTO t : topics) {
		// if (t.getId().equalsIgnoreCase(id)) {
		// topics.remove(t);
		// return;
		// }
		// }
	}

}
