package com.edureka.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import kafka.serializer.Decoder;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

@SpringBootApplication
public class SpringBootWebApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootWebApplication.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringBootWebApplication.class, args);
	}
	
	@Bean
	public VerifiableProperties verifiableProperties(){
		return new kafka.utils.VerifiableProperties();
	}
	@Bean(
		name = "stringDecoder")
	public Decoder<String> stringDecoder() {
		return new StringDecoder(null);
	}

}
