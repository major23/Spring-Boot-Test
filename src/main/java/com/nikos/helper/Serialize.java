package com.nikos.helper;

import java.io.ByteArrayOutputStream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nikos.exceptions.SerializeException;

public class Serialize {

	private static ObjectMapper mapper;

	private Serialize() {

	}

	/**
	 * @return
	 */
	public static ObjectMapper initializeObjectMapper() {
		mapper = new ObjectMapper()
				.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
				.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)
				.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
				.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false)
				.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true)
				.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, true)
				.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
		return mapper;
	}

	public static ObjectMapper initializeObjectMapperDoNotFail() {
		mapper = new ObjectMapper()
				.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
				.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)
				.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
				.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false)
				.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true)
				.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, true)
				.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
		return mapper;
	}

	public static ObjectMapper initializeObjectMapperDoNotFailForNullPrimitives() {
		mapper = new ObjectMapper()
				.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
				.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)
				.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
				.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false)
				.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true)
				.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, true)
				.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
		return mapper;
	}

	/**
	 * @param item
	 * @return
	 * @throws JsonProcessingException
	 */
	public String writeValueAsString(Object item) throws JsonProcessingException {
		mapper = initializeObjectMapper();
		return mapper.writeValueAsString(item);
	}

	/**
	 * @param object
	 * @return
	 * @throws SerializeException
	 */
	public static String serialize(Object object) throws SerializeException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		mapper = initializeObjectMapper();
		String result;
		try {
			mapper.writeValue(bos, object);
			result = mapper.writeValueAsString(object);
		} catch (Exception e) {
			throw new SerializeException(e);
		}
		return result;
	}
}
