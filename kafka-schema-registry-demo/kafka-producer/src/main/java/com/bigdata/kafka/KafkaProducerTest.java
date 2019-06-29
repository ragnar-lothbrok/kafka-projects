package com.bigdata.kafka;


import com.bigdata.kafka.avro.Person;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;

import java.util.Calendar;
import java.util.Properties;
import java.util.UUID;

public class KafkaProducerTest {

    public static void main(String[] args) {
        if(args.length != 2) {
            System.out.println("Please provide broker topic");
            System.exit(0);
        }
        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.MINUTE, 10);
        KafkaProducer<Long, Person> producer = getKafkaProducer(args[0]);
        while(Calendar.getInstance().getTime().getTime() < cal2.getTime().getTime() ) {
            Person person = getPerson();
            producer.send(new ProducerRecord<Long, Person>(args[1],person.getBirthDate(), getPerson()), (metadata, exception) -> {
                if (exception != null) {
                    System.out.println("Exception occured = "+exception);
                }
                if(metadata != null) {
                    System.out.println("producing data to topic  = "+metadata.topic());
                }
            });
            try {
                Thread.sleep(5000);
            }catch(Exception exception) {
                System.out.println("Exception occured = "+exception);
            }
        }
    }

    private static Person getPerson() {
        return Person.newBuilder().setBirthDate(Calendar.getInstance().getTimeInMillis()).setUpdatedDate(Calendar.getInstance().getTimeInMillis()).setFirstName("Ram").setLastName("Raheem").build();
    }

    private static KafkaProducer<Long, Person> getKafkaProducer(String bootStrapServer) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        props.put(ProducerConfig.ACKS_CONFIG, "1");
        props.put(ProducerConfig.RETRIES_CONFIG, "3");
        props.put("partitioner.class", "org.apache.kafka.clients.producer.internals.DefaultPartitioner");
        addAdditionalProps(props);
        return new KafkaProducer<Long, Person>(props);
    }

    private static void addAdditionalProps(Properties props) {
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("max.request.size", 52000000);
        props.put("max.block.ms",10000);
        props.put("request.timeout.ms",5000);
        props.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG,
                "http://localhost:8081,http://localhost:8082,http://localhost:8083");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                new LongSerializer().getClass().getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                KafkaAvroSerializer.class.getName());
    }

    private static String generatingRandomUUID() {
        return UUID.randomUUID().toString();
    }

}
