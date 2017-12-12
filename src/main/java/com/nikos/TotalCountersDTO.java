package com.nikos;

import java.util.concurrent.atomic.AtomicLong;

public class TotalCountersDTO {

	private AtomicLong successMTSMessages;
	private AtomicLong successMTAMessages;
	private AtomicLong successMTACancelMessages;

	private AtomicLong totalMTSMessages;
	private AtomicLong totalMTAMessages;
	private AtomicLong totalMTACancelMessages;

	public TotalCountersDTO() {

	}

	public AtomicLong getSuccessMTSMessages() {
		return successMTSMessages;
	}

	public void setSuccessMTSMessages(AtomicLong successMTSMessages) {
		this.successMTSMessages = successMTSMessages;
	}

	public AtomicLong getSuccessMTAMessages() {
		return successMTAMessages;
	}

	public void setSuccessMTAMessages(AtomicLong successMTAMessages) {
		this.successMTAMessages = successMTAMessages;
	}

	public AtomicLong getSuccessMTACancelMessages() {
		return successMTACancelMessages;
	}

	public void setSuccessMTACancelMessages(AtomicLong successMTACancelMessages) {
		this.successMTACancelMessages = successMTACancelMessages;
	}

	public AtomicLong getTotalMTSMessages() {
		return totalMTSMessages;
	}

	public void setTotalMTSMessages(AtomicLong totalMTSMessages) {
		this.totalMTSMessages = totalMTSMessages;
	}

	public AtomicLong getTotalMTAMessages() {
		return totalMTAMessages;
	}

	public void setTotalMTAMessages(AtomicLong totalMTAMessages) {
		this.totalMTAMessages = totalMTAMessages;
	}

	public AtomicLong getTotalMTACancelMessages() {
		return totalMTACancelMessages;
	}

	public void setTotalMTACancelMessages(AtomicLong totalMTACancelMessages) {
		this.totalMTACancelMessages = totalMTACancelMessages;
	}
}
