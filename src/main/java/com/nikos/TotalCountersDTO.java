package com.nikos;

import java.util.concurrent.atomic.AtomicLong;

public class TotalCountersDTO {

	private AtomicLong topicId;

	public TotalCountersDTO() {

	}

	public AtomicLong getTopicId() {
		return topicId;
	}

	public void setTopicId(AtomicLong topicId) {
		this.topicId = topicId;
	}

}
