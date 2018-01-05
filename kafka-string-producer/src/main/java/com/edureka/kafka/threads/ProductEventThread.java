package com.edureka.kafka.threads;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edureka.kafka.api.EventProducerApi;

public class ProductEventThread implements Callable<Boolean> {

	private static final Logger logger = LoggerFactory.getLogger(ProductEventThread.class);

	private String event;
	private EventProducerApi eventProducerApi;

	public ProductEventThread(String event, EventProducerApi eventProducerApi) {
		super();
		this.event = event;
		this.eventProducerApi = eventProducerApi;
	}

	public Boolean call() throws Exception {
		try {
			eventProducerApi.dispatch(event);
		} catch (Exception e) {
			logger.error("Error occured while pushing event using producer = {} ", e);
			return false;
		}
		return true;
	}

}
