package com.edureka.kafka.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.edureka.kafka.config.props.KafkaProperties;
import com.edureka.kafka.dto.Product;
import com.edureka.kafka.thread.ConsumerThread;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.Decoder;

@Service
public class KafkaConsumerStream {

	private ExecutorService executor;

	private ConsumerConnector consumerConnector;

	private KafkaProperties kafkaProperties;

	@Autowired
	@Qualifier("stringDecoder")
	private Decoder<String> stringDecoder;

	@Autowired
	@Qualifier(value = "productDecoder")
	private Decoder<Product> productDecoder;

	@Autowired
	public KafkaConsumerStream(KafkaProperties kafkaProperties) {
		this.kafkaProperties = kafkaProperties;
		executor = Executors.newFixedThreadPool(kafkaProperties.getPartitionCount());
	}

	@PostConstruct
	public void createConsumerConnector() {
		consumerConnector = kafka.consumer.Consumer.createJavaConsumerConnector(
				createConsumerConfig(kafkaProperties.getBootstrap(), kafkaProperties.getProductgroup()));
		createMultipleConsumerStreams();
	}

	private static ConsumerConfig createConsumerConfig(String zookeeper, String groupId) {
		Properties props = new Properties();
		props.put("zookeeper.connect", zookeeper);
		props.put("group.id", groupId);
		props.put("zookeeper.session.timeout.ms", "5000");
		props.put("zookeeper.sync.time.ms", "250");
		props.put("auto.commit.interval.ms", "1000");
		return new ConsumerConfig(props);
	}

	public void createMultipleConsumerStreams() {
		int partitionCount = kafkaProperties.getPartitionCount();
		Map<String, Integer> topicThreadCountMap = new HashMap<String, Integer>();

		// Define thread count for each topic
		topicThreadCountMap.put(kafkaProperties.getProducttopic(), new Integer(partitionCount));

		// Here we have used a single topic but we can also add
		// multiple topics to topicCount MAP
		Map<String, List<KafkaStream<String, Product>>> consumerStreamsMap = consumerConnector
				.createMessageStreams(topicThreadCountMap, stringDecoder, productDecoder);

		List<KafkaStream<String, Product>> streamList = consumerStreamsMap.get(kafkaProperties.getProducttopic());

		// Creating an object messages consumption streams
		int count = 0;
		for (final KafkaStream<String, Product> stream : streamList) {
			final String streamName = kafkaProperties.getProducttopic() + "-" + kafkaProperties.getProductgroup() + "-"
					+ count + "-" + Calendar.getInstance().getTimeInMillis();
			executor.submit(new ConsumerThread(stream, streamName));
		}
	}

}