package com.nikos.helper;

public class DatabaseConfigurationDTO {

	private String path;
	private String dockerPath;
	private String databaseConnection;
	private String serverMode;
	private String configuration;
	private String topics;

	public DatabaseConfigurationDTO() {
		path = "/webapps/databases/";
		dockerPath = path;
		databaseConnection = "jdbc:h2:~";
		serverMode = ";AUTO_SERVER=TRUE;MULTI_THREADED=TRUE";
		configuration = "CONFIGURATION";
		topics = "TOPICS";
	}

	public String getPath() {
		return path;
	}

	public String getDockerPath() {
		return dockerPath;
	}

	public String getDatabaseConnection() {
		return databaseConnection;
	}

	public String getServerMode() {
		return serverMode;
	}

	public String getConfiguration() {
		return configuration;
	}

	public String getTopics() {
		return topics;
	}

}
