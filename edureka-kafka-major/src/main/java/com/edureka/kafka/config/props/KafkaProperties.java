package com.edureka.kafka.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "producer")
public class KafkaProperties {

	private String bootstrap;
	private String producttopic;
	private String productgroup;
	private Integer partitionCount;
	private String brokers;
	private String fileName;
	private Integer threadCount;

	private Integer minPoolSize;
	private Integer maxPoolSize;

	// producer properties
	private String acks;
	private int retries;
	private String producerType;

	public String getProductgroup() {
		return productgroup;
	}

	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}

	public Integer getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(Integer threadCount) {
		this.threadCount = threadCount;
	}

	public String getBootstrap() {
		return bootstrap;
	}

	public void setBootstrap(String bootstrap) {
		this.bootstrap = bootstrap;
	}

	public String getAcks() {
		return acks;
	}

	public void setAcks(String acks) {
		this.acks = acks;
	}

	public int getRetries() {
		return retries;
	}

	public void setRetries(int retries) {
		this.retries = retries;
	}

	public String getProducerType() {
		return producerType;
	}

	public void setProducerType(String producerType) {
		this.producerType = producerType;
	}

	public Integer getPartitionCount() {
		return partitionCount;
	}

	public void setPartitionCount(Integer partitionCount) {
		this.partitionCount = partitionCount;
	}

	public String getBrokers() {
		return brokers;
	}

	public void setBrokers(String brokers) {
		this.brokers = brokers;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getProducttopic() {
		return producttopic;
	}

	public void setProducttopic(String producttopic) {
		this.producttopic = producttopic;
	}

	public Integer getMinPoolSize() {
		return minPoolSize;
	}

	public void setMinPoolSize(Integer minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public Integer getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(Integer maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

}
