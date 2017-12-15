package com.nikos;

import java.util.concurrent.atomic.AtomicLong;

public class TotalCountersDTO {

	private AtomicLong topicId;
	private AtomicLong userId;

	public TotalCountersDTO() {

	}

	public AtomicLong getTopicId() {
		return topicId;
	}

	public void setTopicId(AtomicLong topicId) {
		this.topicId = topicId;
	}

	public AtomicLong getUserId() {
		return userId;
	}

	public void setUserId(AtomicLong userId) {
		this.userId = userId;
	}

}
