package com.sparkdemo.kafka.serializers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparkdemo.model.TransactionDto;

import kafka.serializer.Decoder;
import kafka.utils.VerifiableProperties;

public class TransactionDecoder implements Decoder<TransactionDto> {

	private static final Logger logger = LoggerFactory.getLogger(TransactionDecoder.class);

	private static final ObjectMapper mapper = new ObjectMapper();

	@Override
	public TransactionDto fromBytes(byte[] bytes) {
		try {
			return mapper.readValue(bytes, TransactionDto.class);
		} catch (Exception e) {
			logger.error("Json processing failed for object {}", e);
		}
		return null;
	}

	public TransactionDecoder() {

	}

	public TransactionDecoder(VerifiableProperties props) {

	}

}
