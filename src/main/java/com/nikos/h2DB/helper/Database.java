package com.nikos.h2DB.helper;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;

import org.h2.jdbc.JdbcSQLException;
import org.slf4j.LoggerFactory;

import com.nikos.helper.DatabaseConfigurationDTO;

public class Database {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Database.class);

	private final int maxRetries = 20;
	private final int maxConnectionRetries = 15;
	private final String driver = "org.h2.Driver";
	private DatabaseConfigurationDTO databaseConfigurationDTO = new DatabaseConfigurationDTO();

	DatabaseDTO databaseTopics;

	private DatabaseDTO getDataBaseConnection(DatabaseType dataType) throws SQLException {
		if (dataType.equals(DatabaseType.TOPICS)) {
			return databaseTopics();
			// } else if (dataType.equals(DatabaseType.MO)) {
			// return databaseMO();
		}

		return null;
	}

	private DatabaseDTO databaseTopics() throws SQLException {
		if (databaseTopics == null) {
			databaseTopics = new DatabaseDTO();
		}
		connect(databaseTopics,
				databaseConfigurationDTO.getDatabaseConnection() + databaseConfigurationDTO.getDockerPath() + databaseConfigurationDTO.getTopics()
						+ databaseConfigurationDTO.getServerMode());
		return databaseTopics;
	}

	private DatabaseDTO connect(DatabaseDTO databaseDTO, String url) throws SQLException {
		int retries = 1;
		Exception ex = null;
		while (databaseDTO.getConnection() == null || databaseDTO.getConnection().isClosed()) {
			try {
				Class.forName(driver);
				databaseDTO.setConnection(DriverManager.getConnection(url));
				databaseDTO.getConnection().setAutoCommit(true);
			} catch (Exception e) {
				// logger.warn("Failed to create connection for Data , " +
				// retries);
				ex = e;
				sleep(200);
			}
			retries++;
			if (retries == maxConnectionRetries) {
				logger.error("Failed to create connection for Data", ex);
			}
		}
		databaseDTO.setStmt(databaseDTO.getConnection().createStatement());
		return databaseDTO;
	}

	public int executeUpdate(String sql, DatabaseType dataType) throws SQLException {
		DatabaseDTO databaseDTO = null;
		int rows = 0;
		boolean failed = true;
		int counter = 0;
		Exception exception = null;
		while (failed && counter < maxRetries) {
			try {
				databaseDTO = getDataBaseConnection(dataType);
				rows = databaseDTO.getStmt().executeUpdate(sql);
				failed = false;
			} catch (Exception ex) {
				exception = ex;
				// SQLite file is locked, retries
				sleep(200);
			}
			counter++;
		}
		close(databaseDTO);
		if (failed) {
			logger.error("Error on executeUpdate " + sql, exception);
			// throw new SQLException("Error on executeUpdate "+sql, exception);
		}
		return rows;
	}

	public DatabaseDTO executeQuery(String sql, DatabaseType dataType) throws SQLException {
		DatabaseDTO databaseDTO = null;
		boolean failed = true;
		int counter = 0;
		Exception exception = null;
		while (failed && counter < maxRetries) {
			try {
				databaseDTO = getDataBaseConnection(dataType);
				databaseDTO.setRs(databaseDTO.getStmt().executeQuery(sql));
				failed = false;
			} catch (JdbcSQLException ex) {
				exception = ex;
				counter = maxRetries;
			} catch (Exception ex) {
				exception = ex;
				// SQLite file is locked, retries
				sleep(200);
			}
			counter++;
		}
		if (failed) {
			logger.error("Error on executeUpdate " + sql, exception);
			// throw new SQLException("Error on executeUpdate "+sql, exception);
		}
		// databaseDTO.getStmt().close();
		return databaseDTO;
	}

	public void close(DatabaseDTO databaseDTO) {
		if (databaseDTO.getRs() != null) {
			try {
				if (!databaseDTO.getRs().isClosed()) {
					databaseDTO.getRs().close();
				}
			} catch (SQLException e) {
				logger.error("", e);
			}
		}
		if (databaseDTO.getStmt() != null) {
			try {
				if (!databaseDTO.getStmt().isClosed() && !databaseDTO.getConnection().isClosed()) {
					databaseDTO.getStmt().close();
				}
			} catch (SQLException e) {
				logger.error("", e);
			}
		}
		try {
			if (!databaseDTO.getConnection().isClosed()) {
				databaseDTO.getConnection().close();
			}
		} catch (SQLException e) {
			logger.error("", e);
		}
	}

	private void sleep(int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - 100) + 100);
		try {
			Thread.sleep(randomNum);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
