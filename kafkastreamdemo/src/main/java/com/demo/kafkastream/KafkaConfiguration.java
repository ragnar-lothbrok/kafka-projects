package com.demo.kafkastream;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;

import java.util.Properties;

public class KafkaConfiguration {

    private Properties streamConfguration(String bootstrapServers) {
        Properties streamConfig = new Properties();
        streamConfig.put(
                StreamsConfig.APPLICATION_ID_CONFIG,
                "test");
        streamConfig.put(
                StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);
        streamConfig.put(
                StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,
                Serdes.String().getClass().getName());
        streamConfig.put(
                StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,
                Serdes.String().getClass().getName());
        return streamConfig;
    }

    private StreamsBuilder buildStream(final String bootstrapServers, final String inTopic, final String outTopic) {
        final KafkaProducer<String, String> producer = createProducer(bootstrapServers);
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, String> kStream = streamsBuilder.stream(inTopic);

        kStream.filter(new Predicate<String, String>() {
            public boolean test(String s, String s2) {
                System.out.print("Event received. Sending using producer == ");
                producer.send(new ProducerRecord<String, String>(outTopic, s, s2));
                return true;
            }
        }).to(outTopic);
        return streamsBuilder;
    }

    public void startStream(String inTopic, String outTopic, String bootstrapServers) {
        final KafkaStreams kafkaStreams = new KafkaStreams(buildStream(bootstrapServers, inTopic, outTopic).build(), streamConfguration(bootstrapServers));
        kafkaStreams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                kafkaStreams.close();
            }
        }));
    }

    private KafkaProducer<String, String> createProducer(String bootstrapServers) {
        return new KafkaProducer<String, String>(getProducerConfig(bootstrapServers));
    }

    private Properties getProducerConfig(String bootstrapServers) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }
}
