package com.kafka.version;

public class Kafkaproperties {
    public static final String zkConnectPort = "2181";
    public static final String groupId = "test-consumer-group";
    public static final String TOPIC = "t";
    public static final String KAFKA_SERVER_URL = "poc-174"; //tdh-24
    public static final int KAFKA_SERVER_PORT = 9092;
    public static final int KAFKA_PRODUCER_BUFFER_SIZE = 64 * 1024;
    public static final int CONNECTION_TIMEOUT = 1000;
    public static final String TOPIC1 = "xj";
    public static final String TOPIC3 = "topic3";
    public static final String CLIENT_ID = "SimpleConsumerDemoClient";

    private Kafkaproperties() {}
}
