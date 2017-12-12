package com.nikos.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nikos.dto.TopicDTO;
import com.nikos.h2DB.DatabaseHelper;
import com.nikos.helper.ConfigurationParamsDTO;
import com.nikos.helper.Serialize;
import com.nikos.helper.SerializeException;

@Service
public class TopicService {

	@Autowired
	private ConfigurationParamsDTO configurationParamsDTO;

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TopicService.class);

	List<TopicDTO> topics = new ArrayList<>(Arrays.asList(
			new TopicDTO("Java", "Java Name", "Java Description")));

	public List<TopicDTO> getAllTopics() {
		// return topics;

		DatabaseHelper database = new DatabaseHelper(configurationParamsDTO);
		try {
			return database.getDatabaseTopics().selectAll();
		} catch (SQLException e) {
			logger.error("Fail to get DB results");
			e.printStackTrace();
			return null;
		}

	}

	public TopicDTO getTopic(String id) {
		for (TopicDTO t : topics) {
			if (t.getId().equals(id)) {
				return t;
			}
		}
		return null;
	}

	public void addTopic(TopicDTO topic) {
		// topics.add(topic);

		try {
			DatabaseHelper database = new DatabaseHelper(configurationParamsDTO);
			database.getDatabaseTopics().insertTopic(topic);
		} catch (Exception ex) {
			try {
				logger.error("Fail to Save in DB " + Serialize.serialize(topic), ex);
			} catch (SerializeException e) {
				e.printStackTrace();
			}
		}

	}

	public void updateTopic(TopicDTO topic) {
		for (int i = 0; i < topics.size(); i++) {
			if (topics.get(i).getId().equalsIgnoreCase(topic.getId())) {
				topics.set(i, topic);
				return;
			}
		}
	}

	public void deleteTopic(String id) {
		for (TopicDTO t : topics) {
			if (t.getId().equalsIgnoreCase(id)) {
				topics.remove(t);
				return;
			}
		}
	}

}
