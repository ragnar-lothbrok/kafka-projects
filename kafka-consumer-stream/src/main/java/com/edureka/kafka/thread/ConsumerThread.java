package com.edureka.kafka.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edureka.kafka.dto.Product;
import com.edureka.kafka.performance.MonitoringCache;
import com.edureka.kafka.performance.MonitoringCache.Caches;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

public class ConsumerThread implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(ConsumerThread.class);

	private KafkaStream<String, Product> m_stream;
	private String streamName;

	public ConsumerThread(KafkaStream<String, Product> m_stream, String streamName) {
		super();
		this.m_stream = m_stream;
		this.streamName = streamName;
	}

	@Override
	public void run() {
		try {
			Product message = null;
			logger.info("started listening stream:  " + streamName);
			ConsumerIterator<String, Product> it = m_stream.iterator();
			while (it.hasNext()) {
				MessageAndMetadata<String, Product> msg = it.next();
				try {
					if (msg != null) {
						message = msg.message();
						logger.info("Message consumed = {} ", message);
						MonitoringCache.updateStats(Caches.PRODUCT_EVENT,1);
					}
				} catch (Exception ex) {
					logger.error("error processing queue for message {} ", ex);
				}
			}
		} catch (Exception ex) {
			logger.error("Exception on message {}", ex);
		}
		logger.info("Shutting down listener stream: " + streamName);
	}
}