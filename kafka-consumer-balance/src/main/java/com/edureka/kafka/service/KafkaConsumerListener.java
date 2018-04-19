package com.edureka.kafka.service;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.edureka.kafka.config.props.KafkaProperties;
import com.edureka.kafka.threads.KafkaConsumerThread;
import com.edureka.kafka.utility.OffsetFileManager;

@Service
public class KafkaConsumerListener implements SmartLifecycle {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerListener.class);

	private volatile boolean running = false;

	@Autowired
	private KafkaProperties kafkaProperties;

	public void start() {
		LOGGER.info("Starting different threads to consume data.");
		ExecutorService consumerExecutorService = Executors.newFixedThreadPool(kafkaProperties.getPartition());
		for (int i = 0; i < kafkaProperties.getPartition(); i++) {
			consumerExecutorService
					.execute(new KafkaConsumerThread(kafkaConsumer(), new OffsetFileManager("prefix")));
		}
		this.running = true;
	}

	public void stop() {
	}

	public boolean isRunning() {
		return running;
	}

	public boolean isAutoStartup() {
		return true;
	}

	public void stop(Runnable callback) {

	}

	public int getPhase() {
		return 0;
	}

	@Bean
	public Deserializer<String> stringKeyDeserializer() {
		return new org.apache.kafka.common.serialization.StringDeserializer();
	}

	public KafkaConsumer<String, String> kafkaConsumer() {
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(
				createConsumerConfig(kafkaProperties.getBootstrap(), kafkaProperties.getTopic()),
				stringKeyDeserializer(), stringKeyDeserializer());
		consumer.subscribe(Collections.singleton(kafkaProperties.getTopic()),
				new ConsumerRebalancerListener(consumer, new OffsetFileManager("prefix")));
		return consumer;
	}

	private static Properties createConsumerConfig(String boostrapSrvs, String groupId) {
		Properties props = new Properties();
		props.put("bootstrap.servers", boostrapSrvs);
		props.put("group.id", groupId);
		props.put("zookeeper.session.timeout.ms", "10000");
		props.put("enable.auto.commit", "false");
		props.put("zookeeper.sync.time.ms", "2500");
		// props.put("auto.commit.interval.ms", "10000");
		props.put("session.timeout.ms", "30000");
		return props;
	}

}