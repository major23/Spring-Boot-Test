package com.nikos.services;

import java.sql.SQLException;
import java.util.List;

import com.nikos.dto.TopicDTO;

public interface TopicService {

	public List<TopicDTO> getAllTopics();

	public TopicDTO getTopic(Long id);

	public TopicDTO addTopic(TopicDTO topic) throws SQLException;

	public TopicDTO updateTopic(TopicDTO topic) throws SQLException;

	public void deleteTopic(String id);

}
