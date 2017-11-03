package com.edureka.kafka.deserializers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.edureka.kafka.dto.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

import kafka.serializer.Decoder;
import kafka.utils.VerifiableProperties;

@Component(value = "productDecoder")
public class ProductDecoder implements Decoder<Product> {

	private static final Logger logger = LoggerFactory.getLogger(ProductDecoder.class);

	private static final ObjectMapper mapper = new ObjectMapper();

	@Override
	public Product fromBytes(byte[] bytes) {
		try {
			return mapper.readValue(bytes, Product.class);
		} catch (Exception e) {
			logger.error("Json processing failed for object {}", e);
		}
		return null;
	}

	public ProductDecoder() {

	}

	public ProductDecoder(VerifiableProperties props) {

	}

}
