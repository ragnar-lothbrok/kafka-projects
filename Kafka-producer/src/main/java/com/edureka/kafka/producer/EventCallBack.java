package com.edureka.kafka.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventCallBack implements Callback {

	private static final Logger logger = LoggerFactory.getLogger(EventCallBack.class);

	@Override
	public void onCompletion(RecordMetadata recordMetadata, Exception e) {
		if (e != null) {
			logger.error("Error while producing message to topic :{} error {} ", recordMetadata, e);
		} else {
			String message = String.format("sent message to topic:%s partition:%s  offset:%s", recordMetadata.topic(),
					recordMetadata.partition(), recordMetadata.offset());
			logger.debug(message);
		}
	}

}
