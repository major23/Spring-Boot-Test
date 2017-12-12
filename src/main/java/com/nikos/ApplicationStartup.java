package com.nikos;

import java.sql.SQLException;

import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);

	/*
	  * This method is called during Spring's startup.
	  * 
	  * @param event Event raised when an ApplicationContext gets initialized or
	  * refreshed.
	  */

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Application.createDatabaseFiles();
		try {
			Application.initDatabases();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("! ! ! APPLICATION STARTUP ! ! !");
		return;
	}

}
