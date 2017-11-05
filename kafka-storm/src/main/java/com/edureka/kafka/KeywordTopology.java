package com.edureka.kafka;

import java.util.HashMap;

import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.topology.TopologyBuilder;

public class KeywordTopology {

	public static void main(String[] args) {

		if(args.length != 2){
			System.out.println("Place command line arguments =>    192.168.0.15:2181 connect-test");
		}
		
		final BrokerHosts zkrHosts = new ZkHosts(args[0]);
		final String kafkaTopic = args[1];
		final SpoutConfig kafkaConf = new SpoutConfig(zkrHosts, kafkaTopic, "", "abc");

		final TopologyBuilder topologyBuilder = new TopologyBuilder();
		// Create KafkaSpout instance using Kafka configuration and add it to
		// topology
		topologyBuilder.setSpout("kafka-spout", new KafkaSpout(kafkaConf), 1);
		topologyBuilder.setBolt("print-messages", new KeywordBolt()).globalGrouping("kafka-spout");

		// Submit topology to local cluster i.e. embedded storm instance in
		// eclipse
		final LocalCluster localCluster = new LocalCluster();
		localCluster.submitTopology("kafka-topology", new HashMap<String, String>(), topologyBuilder.createTopology());

	}
}
