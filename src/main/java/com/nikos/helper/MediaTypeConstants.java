package com.nikos.helper;

import java.nio.charset.Charset;

import org.springframework.http.MediaType;

public class MediaTypeConstants {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType("application", "json", Charset.forName("UTF-8"));

	public static final String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";

}
