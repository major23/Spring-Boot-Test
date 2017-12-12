package com.nikos.helper;

public class ConfigurationParamsDTO {

	private boolean cleanUpDatabasesOnStartUp;
	private boolean logEnabled;
	private boolean databaseEnabled;
	private long httptimeout;

	public ConfigurationParamsDTO() {

	}

	public ConfigurationParamsDTO(boolean cleanUpDatabasesOnStartUp, boolean logEnabled, boolean databaseEnabled, long httptimeout) {
		setCleanUpDatabasesOnStartUp(cleanUpDatabasesOnStartUp);
		setLogEnabled(logEnabled);
		setDatabaseEnabled(databaseEnabled);
		setHttptimeout(httptimeout);
	}

	public boolean getLogEnabled() {
		return logEnabled;
	}

	public void setLogEnabled(boolean logEnabled) {
		this.logEnabled = logEnabled;
	}

	public boolean getDatabaseEnabled() {
		return databaseEnabled;
	}

	public void setDatabaseEnabled(boolean databaseEnabled) {
		this.databaseEnabled = databaseEnabled;
	}

	public boolean getCleanUpDatabasesOnStartUp() {
		return cleanUpDatabasesOnStartUp;
	}

	public void setCleanUpDatabasesOnStartUp(boolean cleanUpDatabasesOnStartUp) {
		this.cleanUpDatabasesOnStartUp = cleanUpDatabasesOnStartUp;
	}

	public long getHttptimeout() {
		return httptimeout;
	}

	public void setHttptimeout(long httptimeout) {
		this.httptimeout = httptimeout;
	}

}
