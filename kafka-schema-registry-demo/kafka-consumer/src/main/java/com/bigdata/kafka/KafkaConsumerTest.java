package com.bigdata.kafka;

import com.bigdata.kafka.avro.Person;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerTest {

    public static void main(String[] args) {
        if(args.length != 2) {
            System.out.println("Please provide broker topic");
            System.exit(0);
        }
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, args[0]);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka consumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                new LongDeserializer().getClass().getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                KafkaAvroDeserializer.class.getName());
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");
        props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG,
                "http://localhost:8081,http://localhost:8082,http://localhost:8083");

        final Consumer<Long, Person> consumer =  new KafkaConsumer<Long, Person>(props);

        consumer.subscribe(Collections.singletonList(args[1]));

        while(true) {
            final ConsumerRecords<Long, Person> records =
                    consumer.poll(Duration.ofSeconds(5));
            if(records != null ){
                records.forEach(record -> {
                    Person person = record.value();
                    System.out.printf("%s %d %d %s \n", record.topic(),
                            record.partition(), record.offset(), person);
                });
            }
        }
    }
}
