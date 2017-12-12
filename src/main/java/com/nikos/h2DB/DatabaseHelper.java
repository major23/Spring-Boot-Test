package com.nikos.h2DB;

import java.sql.SQLException;

import com.nikos.h2DB.databases.DatabaseTopics;
import com.nikos.h2DB.helper.Database;
import com.nikos.helper.ConfigurationParamsDTO;

public class DatabaseHelper {

	private Database database;
	private ConfigurationParamsDTO configurationParamsDTO;

	DatabaseTopics topics;

	public DatabaseHelper(ConfigurationParamsDTO configurationParamsDTO) {
		this.configurationParamsDTO = configurationParamsDTO;
	}

	public Database database() {
		if (database == null) {
			database = new Database();
		}
		return database;
	}

	public DatabaseTopics getDatabaseTopics() {
		if (topics == null) {
			topics = new DatabaseTopics(configurationParamsDTO);
		}
		return topics;
	}

	public void createTables() throws SQLException {
		getDatabaseTopics().createTables();
	}

	public void dropTables() throws SQLException {
		getDatabaseTopics().dropTables();

	}

}
