package com.test.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksIterator;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

public class App {

    static {
        RocksDB.loadLibrary();
    }

    public static void main(String[] args) throws Exception {


        RocksDB rocksDB = RocksDB.open("/tmp/rocksdb");
        Runtime.getRuntime().addShutdownHook(new Thread(rocksDB::close, "rocksdb-shutdown-thread"));

        KafkaPublishService kafkaPublishService = new KafkaPublishService(rocksDB);


        Timer timer = new Timer();
        ScheduledTask scheduledTask = new ScheduledTask(kafkaPublishService);
        timer.schedule(scheduledTask, 0, 100);


        Timer timer2 = new Timer();
        ReadRocksDBTask readRocksDBTask = new ReadRocksDBTask(rocksDB, kafkaPublishService);
        timer2.schedule(readRocksDBTask, 0, 10000);

    }

    static class ScheduledTask extends TimerTask {

        private AtomicLong atomicLong = new AtomicLong(100);
        private KafkaPublishService kafkaPublishService;
        private ObjectMapper objectMapper;

        ScheduledTask(KafkaPublishService kafkaPublishService) {
            this.kafkaPublishService = kafkaPublishService;
            this.objectMapper = new ObjectMapper();
        }

        public void run() {
            Organisation organisation = new Organisation();
            organisation.setDescription("description");
            organisation.setEmployees(10);
            organisation.setOrganisation_name("eary stage startup");
            organisation.setId(atomicLong.incrementAndGet());

            try {
                kafkaPublishService.publishData(organisation.getId().toString(), objectMapper.writeValueAsString(organisation));
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }
    }

    static class ReadRocksDBTask extends TimerTask {

        private AtomicLong atomicLong = new AtomicLong();
        private RocksDB rocksDB;
        private KafkaPublishService kafkaPublishService;

        ReadRocksDBTask(RocksDB rocksDB, KafkaPublishService kafkaPublishService) {
            this.rocksDB = rocksDB;
            this.kafkaPublishService = kafkaPublishService;
        }

        public void run() {
            try {
                RocksIterator iterator = rocksDB.newIterator();
                iterator.seekToFirst();
                while (iterator.isValid()) {
                    System.out.println("Keys = " + new String(iterator.key()));
                    if (kafkaPublishService.publishData(new String(iterator.key()), new String(iterator.value()))) {
                        rocksDB.delete(iterator.key());
                        System.out.println("Deleted key = " + new String(iterator.key()));
                    }
                    iterator.next();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
