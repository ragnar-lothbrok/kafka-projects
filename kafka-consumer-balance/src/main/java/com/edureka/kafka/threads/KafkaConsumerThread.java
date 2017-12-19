package com.edureka.kafka.threads;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edureka.kafka.utility.OffsetFileManager;

public class KafkaConsumerThread extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerThread.class);

	private KafkaConsumer<String, String> kafkaConsumer;

	private OffsetFileManager offsetFileManager;

	public KafkaConsumerThread(final KafkaConsumer<String, String> kafkaConsumer,
			final OffsetFileManager offsetFileManager) {
		super();
		this.kafkaConsumer = kafkaConsumer;
		this.offsetFileManager = offsetFileManager;
	}

	@Override
	public void run() {
		logger.info("Consumer thread is started.");
		try {
			while (true) {
				try {
					ConsumerRecords<String, String> records = this.kafkaConsumer.poll(1000);
					for (ConsumerRecord<String, String> record : records) {
						logger.info("Consuming from topic = {}, partition = {}, offset = {}, key = {}, value = {}",
								record.topic(), record.partition(), record.offset(), record.key(), record.value());
						offsetFileManager.saveOffsetInOffsetManager(record.topic(), record.partition(),
								record.offset());
					}
					
				} catch (Exception e) {
					logger.error("Exception occured while consuming event = {} ", e);
				}
			}
		} finally {
			this.kafkaConsumer.close();
		}
	}

}
