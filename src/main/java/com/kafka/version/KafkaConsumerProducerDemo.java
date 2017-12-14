package com.kafka.version;

public class KafkaConsumerProducerDemo {
    public static void main(String[] args)
    {
//        Producer producerThread = new Producer(Kafkaproperties.TOPIC);
//        producerThread.start();

        Consumer1 consumerThread = new Consumer1(Kafkaproperties.TOPIC,Kafkaproperties.TOPIC1);
        consumerThread.start();

    }
}
