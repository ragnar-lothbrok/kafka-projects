package com.edureka.kafka.service;

import java.util.Collection;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edureka.kafka.utility.OffsetFileManager;

public class ConsumerRebalancerListener implements ConsumerRebalanceListener {

	private static final Logger logger = LoggerFactory.getLogger(ConsumerRebalancerListener.class);

	private KafkaConsumer<String, String> kafkaConsumer;

	private OffsetFileManager offsetManager;

	public ConsumerRebalancerListener(final KafkaConsumer<String, String> kafkaConsumer,
			final OffsetFileManager offsetFileManager) {
		this.kafkaConsumer = kafkaConsumer;
		this.offsetManager = offsetFileManager;
	}

	@Override
	public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
		logger.info("inside partition assigned.");
		for (TopicPartition partition : partitions) {
			offsetManager.saveOffsetInOffsetManager(partition.topic(), partition.partition(),
					kafkaConsumer.position(partition));
		}
	}

	@Override
	public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
		logger.info("inside partition revoked.");
		for (TopicPartition partition : partitions) {
			kafkaConsumer.seek(partition,
					offsetManager.readOffsetFromoffsetManager(partition.topic(), partition.partition()));
		}
	}

}
