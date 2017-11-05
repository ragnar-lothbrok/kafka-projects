package com.sparkdemo.kafka.service;

import java.util.List;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sparkdemo.kafka.configuration.KafkaProperties;
import com.sparkdemo.model.TransactionDto;

@Service
public class ProducerService {

	@Autowired
	private KafkaProducer<String, TransactionDto> producer;

	@Autowired
	private KafkaProperties kafkaroperties;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProducerService.class);

	public boolean dispatch(TransactionDto message) {
		ProducerRecord<String, TransactionDto> record = new ProducerRecord<String, TransactionDto>(
				kafkaroperties.getTopic(), message.getTransactionId(), message);
		try {
			RecordMetadata recordMetadata = this.producer.send(record).get();
			LOGGER.info("topic = {}, partition = {}, offset = {}, workUnit = {}", recordMetadata.topic(),
					recordMetadata.partition(), recordMetadata.offset(), message);
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void dispatch(List<TransactionDto> messages) {
		if (messages != null) {
			for (TransactionDto message : messages) {
				dispatch(message);
			}
		}
	}
}
