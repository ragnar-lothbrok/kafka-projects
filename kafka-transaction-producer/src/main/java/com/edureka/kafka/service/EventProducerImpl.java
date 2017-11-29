package com.edureka.kafka.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.edureka.kafka.api.EventProducerApi;
import com.edureka.kafka.config.props.KafkaProperties;
import com.edureka.kafka.dto.Transaction;
import com.edureka.kafka.performance.MonitoringCache;
import com.edureka.kafka.performance.MonitoringCache.Caches;
import com.edureka.kafka.producer.EventCallBack;
import com.edureka.kafka.utility.FileUtility;

@Service
public class EventProducerImpl implements EventProducerApi {

	@Autowired
	private KafkaProperties kafkaProperties;

	private static final Logger LOGGER = LoggerFactory.getLogger(EventProducerImpl.class);

	@Autowired
	@Qualifier("productEventProducer")
	Producer<String, Transaction> eventProducer;

	@PostConstruct
	public void init() {
		List<Transaction> productIst = FileUtility.readFile(kafkaProperties.getFilePath());
		for (Transaction product : productIst) {
			dispatch(product);
		}
	}

	public void dispatch(Transaction transaction) {
		long startTime = System.currentTimeMillis();
		LOGGER.info("Event dispatch started = {} ", transaction);
		ProducerRecord<String, Transaction> data = new ProducerRecord<String, Transaction>(
				kafkaProperties.getProducttopic(), transaction.getTransactionId(), transaction);
		eventProducer.send(data, new EventCallBack());
		MonitoringCache.updateStats(Caches.PRODUCT_EVENT, (System.currentTimeMillis() - startTime), 1);
	}

}
