package com.nikos.h2DB.helper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseDTO {

	private Connection connection;
	private ResultSet rs;
	private Statement stmt;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}

}
