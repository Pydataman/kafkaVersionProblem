package com.kafka.version;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class Consumer1 extends Thread{
    private final ConsumerConnector consumer;
    private final String topic;
    public String str;
    private final kafka.javaapi.producer.Producer<Integer, String> producer;
    private final String newtopic;
    public Consumer1(String topic,String newtopic){
        Properties pro=new Properties();
        pro.put("zookeeper.connect",Kafkaproperties.KAFKA_SERVER_URL+":"+Kafkaproperties.zkConnectPort);
        pro.put("group.id",Kafkaproperties.groupId);
        pro.put("zookeeper.session.timeout.ms","500");
        pro.put("zookeeper.sync.time.ms","200");
        pro.put("auto.commit.interval.ms","1000");
        consumer=kafka.consumer.Consumer.createJavaConsumerConnector(new ConsumerConfig(pro));
        this.topic=topic;
        Properties props=new Properties();
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("metadata.broker.list", "tdh-24:9092");
        // Use random partitioner. Don't need the key type. Just set it to Integer.
        // The message is of type String.
        producer = new kafka.javaapi.producer.Producer<Integer, String>(new ProducerConfig(props));
        this.newtopic=newtopic;
    }

    @Override
    public void run() {
        Map<String,Integer> topicCountMap=new HashMap<String, Integer>();
        topicCountMap.put(topic,new Integer(1));
        Map<String,List<KafkaStream<byte[],byte[]>>> consumerMap=consumer.createMessageStreams(topicCountMap);
        KafkaStream<byte[],byte[]> stream = consumerMap.get(topic).get(0);
        ConsumerIterator<byte[],byte[]> it = stream.iterator();
        while(it.hasNext()){
            str=new String(it.next().message());
            producer.send(new KeyedMessage<Integer, String>(newtopic, str));
        }
    }
    public static void main(String[] args){
        Consumer con=new Consumer(Kafkaproperties.TOPIC);
        con.start();
    }
}
