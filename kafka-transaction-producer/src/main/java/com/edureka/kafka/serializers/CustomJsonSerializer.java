package com.edureka.kafka.serializers;

import java.util.Map;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomJsonSerializer<T> implements Serializer<T> {

	private final ObjectMapper objectMapper;

	public CustomJsonSerializer() {
		this.objectMapper = new ObjectMapper();
	}

	public void configure(Map<String, ?> configs, boolean isKey) {
		// nothing to do
	}

	public byte[] serialize(String topic, T data) {
		byte[] bytes = null;
		try {
			bytes = this.objectMapper.writeValueAsBytes(data);
		} catch (JsonProcessingException e) {
			throw new SerializationException(e);
		}
		return bytes;
	}

	public void close() {
		// nothing to do
	}

}
