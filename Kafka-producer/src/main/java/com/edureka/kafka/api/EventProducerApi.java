package com.edureka.kafka.api;

import com.edureka.kafka.dto.Product;

public interface EventProducerApi {

	void dispatch(Product balanceEvent);

}
