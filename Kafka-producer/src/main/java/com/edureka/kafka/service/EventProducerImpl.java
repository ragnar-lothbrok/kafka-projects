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
import com.edureka.kafka.dto.Product;
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
	Producer<String, Product> productEventProducer;

	@PostConstruct
	public void init() {
		List<Product> productIst = FileUtility.readFile(kafkaProperties.getFilePath());
		for (Product product : productIst) {
			dispatch(product);
		}
	}

	@Override
	public void dispatch(Product product) {
		long startTime = System.currentTimeMillis();
		LOGGER.info("Event dispatch started = {} ", product);
		ProducerRecord<String, Product> data = new ProducerRecord<String, Product>(kafkaProperties.getProducttopic(),
				product.getSupc(), product);
		productEventProducer.send(data, new EventCallBack());
		MonitoringCache.updateStats(Caches.PRODUCT_EVENT, (System.currentTimeMillis() - startTime), 1);
	}

}
