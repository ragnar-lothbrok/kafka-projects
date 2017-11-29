package com.edureka.kafka.api;

import com.edureka.kafka.dto.Transaction;

public interface EventProducerApi {

	void dispatch(Transaction balanceEvent);

}
