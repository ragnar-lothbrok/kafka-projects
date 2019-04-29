package com.demo.kafkastream;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        KafkaConfiguration kafkaConfiguration = new KafkaConfiguration();
        kafkaConfiguration.startStream("test3","test4","localhost:9092");
    }


}
