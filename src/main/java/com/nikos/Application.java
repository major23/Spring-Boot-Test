package com.nikos;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.h2.tools.DeleteDbFiles;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.nikos.h2DB.DatabaseHelper;
import com.nikos.helper.ConfigurationParamsDTO;
import com.nikos.helper.DatabaseConfigurationDTO;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication // same as @Configuration @EnableAutoConfiguration
						// @ComponentScan
public class Application extends SpringBootServletInitializer {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Application.class);
	private static final String CONFIG_NAME = "nikos-application";

	static String ownerName;
	static String ownerLastName;
	private ThreadPoolTaskExecutor executor;

	// Database Names
	private static DatabaseConfigurationDTO databaseConfigurationDTO = new DatabaseConfigurationDTO();

	// Total Counters
	private static TotalCountersDTO totalCounters;

	// Configuration params
	private static ConfigurationParamsDTO configurationParamsDTO;

	// used possibly for overriding the properties in docker
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class).properties(ImmutableMap.of("spring.config.name", CONFIG_NAME));
	}

	public static void main(String[] args) throws Exception {
		// Here we state the YML config file for properties and explicitly
		// declare this app as a Web app

		new SpringApplicationBuilder(Application.class).properties(ImmutableMap.of("spring.config.name", CONFIG_NAME)).web(true).build().run(args);
		logger.info("PROJECT OWNER: " + getOwnerName() + " " + getOwnerLastName());
	}

	@PostConstruct
	public void laucnhComplete() {
		logger.info("Dependency injection is done!");
	}

	@Bean(name = "threadExecutor")
	public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
		executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setThreadNamePrefix("Executor_thread");
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.initialize();
		return executor;
	}

	@Value("${threadpool.corepoolsize:2000}")
	private int corePoolSize;

	@Value("${threadpool.maxpoolsize:4000}")
	private int maxPoolSize;

	@Value("${time-zone}")
	private String timeZone;

	@Bean
	public TotalCountersDTO totalCounters() {
		return getTotalCounters();
	}

	@Bean
	public static ConfigurationParamsDTO configurationParamsDTO() {
		if (configurationParamsDTO == null) {
			configurationParamsDTO = new ConfigurationParamsDTO(true, true, true, 30000);
		}
		return configurationParamsDTO;
	}

	static void createDatabaseFiles() {
		logger.info("Create Databases");
		try {
			createDatabaseFile(databaseConfigurationDTO.getTopics());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void createDatabaseFile(String databaseConfigurationName) throws IOException {
		String current = new java.io.File(".").getCanonicalPath();
		File databaseFile = new File(current + databaseConfigurationDTO.getDockerPath(), databaseConfigurationName);
		databaseFile.getParentFile().mkdirs();
		if (!fileExists(databaseFile.getAbsolutePath())) {
			DeleteDbFiles.execute(current + databaseConfigurationDTO.getDockerPath(), databaseConfigurationName, true);
			if (!databaseFile.exists()) {
				try {
					logger.info("Create " + databaseConfigurationName);
					databaseFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			logger.info("DB File " + databaseConfigurationName + " exists");
		}
	}

	static void initDatabases() throws SQLException {
		// System.setProperty(CommonParams.HTTP_READ_TIME_OUT,
		// Integer.toString(Params.HTTPTIMEOUT));
		DatabaseHelper databaseHelper = new DatabaseHelper(configurationParamsDTO);
		if (configurationParamsDTO.getCleanUpDatabasesOnStartUp()) {
			try {
				databaseHelper.dropTables();
				databaseHelper.createTables();
			} catch (SQLException e) {
				logger.error("", e);
			}
		}
	}

	private TotalCountersDTO getTotalCounters() {
		if (totalCounters == null) {
			totalCounters = new TotalCountersDTO();
			totalCounters.setTopicId(new AtomicLong(0));
			totalCounters.setUserId(new AtomicLong(0));
		}
		return totalCounters;
	}

	@Bean
	public RestTemplate restTemplate(ObjectMapper objectMapper) {
		List<HttpMessageConverter<? extends Object>> converters = Arrays.asList(
				new ByteArrayHttpMessageConverter(),
				new StringHttpMessageConverter(),
				new ResourceHttpMessageConverter(),
				new SourceHttpMessageConverter<>(),
				new AllEncompassingFormHttpMessageConverter(),
				new MappingJackson2HttpMessageConverter(objectMapper));

		HttpClient httpClient = HttpClientBuilder.create()
				.setMaxConnTotal(40)
				.setMaxConnPerRoute(20)
				.build();

		RestTemplate restTemplate = new RestTemplate(converters);
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

		return restTemplate;
	}

	public static String getOwnerName() {
		return ownerName;
	}

	// default = John
	// When the param is NOT present in config file it takes default
	// If present but empty then it will be empty e.g.""
	@Value("${configuration.owner.name:John}")
	public void setOwnerName(String name) {
		Application.ownerName = name;
	}

	public static String getOwnerLastName() {
		return ownerLastName;
	}

	// passing default value Doe
	@Value("${configuration.owner.lastName: Doe}")
	public void setOwnerLastName(String lastName) {
		Application.ownerLastName = lastName;
	}

	@Value("${startUpOptions.cleanUpDatabasesOnStartUp:true}")
	private void setCleanUpDatabasesOnStartUp(Boolean override) {
		configurationParamsDTO().setCleanUpDatabasesOnStartUp(override);
	}

	@Value("${startUpOptions.enableDatabaseOnStartUp:true}")
	private void setEnableDatabaseOnStartUp(Boolean override) {
		configurationParamsDTO().setDatabaseEnabled(override);
	}

	@Value("${startUpOptions.enableLogging:true}")
	private void setEnableLogOnStartUp(Boolean override) {
		configurationParamsDTO().setLogEnabled(override);
	}

	@Value("${startUpOptions.httpTimeOut:30000}")
	private void setHttpTimeOut(Long httpTimeout) {
		configurationParamsDTO().setHttptimeout(httpTimeout);
	}

	private static boolean fileExists(String filePathString) {
		File f = new File(filePathString);
		if (f.exists() && !f.isDirectory()) {
			return true;
		}
		return false;
	}

}
