package com.test.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.rocksdb.RocksDB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class KafkaPublishService {

    private RocksDB rocksDB;

    KafkaPublishService(RocksDB rocksDB) {
        this.rocksDB = rocksDB;
    }

    public boolean publishData(String key, String body) {
        String topic = "test";
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        ProducerRecord<String, String> data = new ProducerRecord<String, String>("test", key,
                body);
        ProducerFactory.getKafkaProducerInstance().send(data, (metadata, exception) -> {
            if (exception != null) {
                atomicBoolean.set(false);
                System.out.println("Exception occured while publishing..." + exception);
                try{
                    rocksDB.put(data.key().getBytes(), data.value().getBytes());
                } catch(Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        return atomicBoolean.get();
    }
}
