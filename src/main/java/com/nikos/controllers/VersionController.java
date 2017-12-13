package com.nikos.controllers;

import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletContext;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {

	private final ServletContext servletContext;

	public VersionController(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@RequestMapping(value = "/version", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public String version() throws Exception {

		return new String(Files.readAllBytes(Paths.get(servletContext.getResource("/version").toURI())));

	}
}
