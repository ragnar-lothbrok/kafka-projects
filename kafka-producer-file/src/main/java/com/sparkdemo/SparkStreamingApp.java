//package com.sparkdemo;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.streaming.Duration;
//import org.apache.spark.streaming.api.java.JavaPairInputDStream;
//import org.apache.spark.streaming.api.java.JavaStreamingContext;
//import org.apache.spark.streaming.kafka.KafkaUtils;
//
//import com.sparkdemo.kafka.serializers.TransactionDecoder;
//import com.sparkdemo.model.TransactionDto;
//
//import kafka.serializer.StringDecoder;
//
//public class SparkStreamingApp {
//
//	public static void main(String[] args) {
//
//		SparkConf conf = new SparkConf().setAppName("kafka-sandbox").setMaster("local[*]");
//		JavaSparkContext sc = new JavaSparkContext(conf);
//		JavaStreamingContext ssc = new JavaStreamingContext(sc, new Duration(2000l));
//
//		Map<String, String> kafkaParams = new HashMap<>();
//		kafkaParams.put("metadata.broker.list", "localhost:9092");
//		kafkaParams.put("auto.offset.reset", "smallest");
//		Set<String> topics = Collections.singleton("transaction");
//
//		JavaPairInputDStream<String, TransactionDto> directKafkaStream = KafkaUtils.createDirectStream(ssc,
//				String.class, TransactionDto.class, StringDecoder.class, TransactionDecoder.class, kafkaParams, topics);
//
//		directKafkaStream.foreachRDD(rdd -> {
//			System.out.println(
//					"--- New RDD with " + rdd.partitions().size() + " partitions and " + rdd.count() + " records");
//			rdd.foreach(record -> {
//				System.out.println("Got the record : " + record._2);
//			});
//		});
//
//		ssc.start();
//		ssc.awaitTermination();
//	}
//}
