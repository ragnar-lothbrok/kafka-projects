package com.edureka.kafka.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "consumer")
public class KafkaProperties {

	private String bootstrap;
	private String topic;
	private String groupId;
	private Integer partition;

	public String getBootstrap() {
		return bootstrap;
	}

	public void setBootstrap(String bootstrap) {
		this.bootstrap = bootstrap;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Integer getPartition() {
		return partition;
	}

	public void setPartition(Integer partition) {
		this.partition = partition;
	}

}
