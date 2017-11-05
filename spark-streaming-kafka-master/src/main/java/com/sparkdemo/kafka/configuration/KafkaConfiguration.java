package com.sparkdemo.kafka.configuration;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sparkdemo.kafka.serializers.JsonSerializer;
import com.sparkdemo.model.TransactionDto;

@Configuration
public class KafkaConfiguration {

	@Bean
	public Serializer<String> stringKeySerializer() {
		return new StringSerializer();
	}

	@Bean
	public Serializer<TransactionDto> transactionSerializer() {
		return new JsonSerializer<TransactionDto>();
	}

	@Bean
	public KafkaProducer<String, TransactionDto> clickProducer(KafkaProperties kafkaProperties) {
		Properties kafkaProps = new Properties();
		kafkaProps.put("bootstrap.servers", kafkaProperties.getBrokers());
		kafkaProps.put("acks", "1");
		kafkaProps.put("retries", "3");
		kafkaProps.put("partitioner.class", "org.apache.kafka.clients.producer.internals.DefaultPartitioner");
		return new KafkaProducer<String, TransactionDto>(kafkaProps, stringKeySerializer(), transactionSerializer());
	}

}