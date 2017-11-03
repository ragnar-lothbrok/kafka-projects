package com.edureka.kafka.configurations;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.edureka.kafka.config.props.KafkaProperties;
import com.edureka.kafka.dto.Product;
import com.edureka.kafka.serializers.CustomJsonSerializer;

@Configuration
public class KafkaProducerConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(KafkaProducerConfiguration.class);

	@Autowired
	private KafkaProperties kafkaProperties;

	@Bean
	public Serializer<String> stringKeySerializer() {
		return new StringSerializer();
	}

	@Bean
	public Producer<String, Product> productEventProducer() {
		logger.debug("Configuring Balance Producer.");
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrap());
		props.put(ProducerConfig.ACKS_CONFIG, kafkaProperties.getAcks());
		props.put(ProducerConfig.RETRIES_CONFIG, kafkaProperties.getRetries());
		props.put("partitioner.class", "org.apache.kafka.clients.producer.internals.DefaultPartitioner");
		Producer<String, Product> producer = new KafkaProducer<String, Product>(props, stringKeySerializer(),
				new CustomJsonSerializer<Product>());
		logger.info("Balance Event Producer created.");
		return producer;
	}

	@Bean
	public ThreadPoolTaskExecutor productTaskExecutor() {
		ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
		pool.setCorePoolSize(kafkaProperties.getMinPoolSize());
		pool.setMaxPoolSize(kafkaProperties.getMaxPoolSize());
		pool.setWaitForTasksToCompleteOnShutdown(true);
		return pool;
	}

}
