package com.demo.kafkastream;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.processor.*;

import java.util.*;

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

        streamConfig.put("auto.offset.reset", "latest");
        return streamConfig;
    }

    private int count = 100;
    private Long time = Calendar.getInstance().getTimeInMillis();

    private StreamsBuilder buildStream(final String bootstrapServers, final String inTopic, final String outTopic) {
        final KafkaProducer<String, String> producer = createProducer(bootstrapServers);
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, String> kStream = streamsBuilder.stream(Arrays.asList(inTopic, outTopic));

        kStream.filter(new Predicate<String, String>() {
            public boolean test(String s, String s2) {
                //System.out.print("Event received. Sending using producer == ");
                ProducerRecord<String, String> record = new ProducerRecord<String, String>(inTopic, null, time,s, (count++)+"");
                record.headers().add("header1",time.toString().getBytes());
                producer.send(record);
                return true;
            }
        }).transformValues(() -> new ValueTransformer<String, String>() {
            private ProcessorContext context;

            public void init(ProcessorContext processorContext) {
                this.context =processorContext;
            }

            public String transform(String s) {
                if(context.headers() !=  null ) {
                    Map<String,byte[]>  headers = readHeaders(context.headers());
                    try {
                        System.out.println("Time taken == "+ (Calendar.getInstance().getTimeInMillis() - Long.parseLong(new String(headers.get("header1")))));
                    }catch(Exception exception){

                    }
                }
                return s;
            }

            public void close() {

            }

        }).to(outTopic);
        return streamsBuilder;
    }

    private Map<String,byte[]> readHeaders(Headers headers){
        Map<String,byte[]> headerMap = new HashMap<>();
        Iterator<Header> iterator = headers.iterator();
        while (iterator.hasNext()) {
            Header next = iterator.next();
            headerMap.put(next.key(),next.value());
        }
        return headerMap;
    }

    public void startStream(String inTopic, String outTopic, String bootstrapServers) {
        Topology topology = buildStream(bootstrapServers, inTopic, outTopic).build();
        final KafkaStreams kafkaStreams = new KafkaStreams(topology, streamConfguration(bootstrapServers));
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
