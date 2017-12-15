package com.nikos.h2DB.databases;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

import com.nikos.dto.TopicDTO;
import com.nikos.h2DB.helper.Database;
import com.nikos.h2DB.helper.DatabaseDTO;
import com.nikos.h2DB.helper.DatabaseType;
import com.nikos.helper.ConfigurationParamsDTO;

import ch.qos.logback.classic.Logger;

public class DatabaseTopics {

	private final org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class);

	private Database database;
	private ConfigurationParamsDTO configurationParamsDTO;
	private DatabaseType dbType;

	// Table
	private final String TABLE = "TOPICS";
	// Columns
	private final String ID = "id";
	private final String NAME = "name";
	private final String DESCRIPTION = "description";

	private final String varChar = " varchar(1024),";

	public DatabaseTopics(ConfigurationParamsDTO configurationParamsDTO) {
		this.database = new Database();
		this.dbType = DatabaseType.TOPICS;
		this.configurationParamsDTO = configurationParamsDTO;
	}

	public List<String> getColumns() {
		List<String> columns = new ArrayList<String>();
		columns.add(ID);
		columns.add(NAME);
		columns.add(DESCRIPTION);
		return columns;
	}

	public String getDataColumnstoString() {
		String columns = "";
		for (String col : getColumns()) {
			columns += col + ",";
		}
		return columns.substring(0, columns.length() - 1);
	}

	public String getColumnstoStringCreate() {
		String columns = "";
		for (String col : getColumns()) {
			columns += col + varChar;
		}
		return columns.substring(0, columns.length() - 1);
	}

	public void createTables() throws SQLException {
		logger.info("Create " + TABLE + " Table");
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE + "(" + getColumnstoStringCreate() + ");";
		database.executeUpdate(sql, dbType);
	}

	public void dropTables() throws SQLException {
		logger.info("Drop " + TABLE + " Table");
		String sql = "DROP TABLE IF EXISTS " + TABLE;
		database.executeUpdate(sql, dbType);
	}

	public void deleteTables() throws SQLException {
		logger.info("Delete " + TABLE + " Table");
		String sql = "Delete from " + TABLE;
		database.executeUpdate(sql, dbType);
	}

	public int deleteTopic(Long id) throws SQLException {
		String sql = "DELETE from " + TABLE + " where " + ID + "='" + id + "'";
		return database.executeUpdate(sql, dbType);
	}

	public void insertTopics(List<TopicDTO> topics) throws SQLException {
		if (configurationParamsDTO.getDatabaseEnabled()) {
			for (TopicDTO temp : topics) {
				String sql = "INSERT INTO " + TABLE + " (" + getDataColumnstoString() + ") " +
						"VALUES ('" + temp.getId() + "','" + temp.getName() + "','" + temp.getDescription() + "');";
				database.executeUpdate(sql, dbType);
			}
		}
	}

	public void insertTopic(TopicDTO topic) throws SQLException {
		if (configurationParamsDTO.getDatabaseEnabled()) {
			String sql = "INSERT INTO " + TABLE + " (" + getDataColumnstoString() + ") " +
					"VALUES ('" + topic.getId() + "','" + topic.getName() + "','" + topic.getDescription() + "');";
			database.executeUpdate(sql, dbType);
		}
	}

	public void updateTopic(TopicDTO topic) throws SQLException {
		if (configurationParamsDTO.getDatabaseEnabled()) {
			String sql = "UPDATE " + TABLE + " SET " + NAME + "='" + topic.getName() + "'," + DESCRIPTION + "='" + topic.getDescription() + "'"
					+ " WHERE " + ID + "=" + topic.getId();
			database.executeUpdate(sql, dbType);
		}
	}

	public List<TopicDTO> selectAll() throws SQLException {
		String sql = "SELECT " + getDataColumnstoString() + " FROM " + TABLE;
		try {
			return selectDB(sql);
		} catch (NullPointerException npe) {
			return null;
		}
	}

	public TopicDTO selectById(Long id) throws SQLException {
		String sql = "SELECT " + getDataColumnstoString() + " FROM " + TABLE + " WHERE " + ID + "='" + id + "'";
		try {
			return selectDB(sql).get(0);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	private List<TopicDTO> selectDB(String sql) throws SQLException {
		List<TopicDTO> list = new ArrayList<TopicDTO>();
		DatabaseDTO databaseDTO = database.executeQuery(sql, dbType);
		try {
			while (databaseDTO.getRs().next()) {
				TopicDTO topic = new TopicDTO();
				topic.setId(databaseDTO.getRs().getLong(ID));
				topic.setName(databaseDTO.getRs().getString(NAME));
				topic.setDescription(databaseDTO.getRs().getString(DESCRIPTION));
				list.add(topic);
			}
		} catch (Exception ex) {
			logger.error("", ex);
		} finally {
			database.close(databaseDTO);
		}
		return list;
	}

}
