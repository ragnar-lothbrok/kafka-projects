package com.edureka.kafka.deserializers;

import java.io.IOException;
import java.util.Map;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomJsonDeserializer<T> implements Deserializer<T> {
	private final ObjectMapper objectMapper;

	private final Class<T> toClazz;

	public CustomJsonDeserializer(Class<T> toClazz) {
		this(new ObjectMapper(), toClazz);
	}

	public CustomJsonDeserializer(ObjectMapper objectMapper, Class<T> toClazz) {
		this.objectMapper = objectMapper;
		this.toClazz = toClazz;
	}

	public void configure(Map<String, ?> configs, boolean isKey) {
		// nothing to do
	}

	public T deserialize(String topic, byte[] data) {
		T t = null;
		try {
			t = this.objectMapper.readValue(data, toClazz);
		} catch (IOException e) {
			throw new SerializationException(e);
		}
		return t;
	}

	public void close() {
		// nothing to do
	}
}