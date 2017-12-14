package com.kafka.version;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class Consumer extends Thread{
    private final ConsumerConnector consumer;
    private final String topic;
    public String str;
    public Consumer(String topic){
        Properties pro=new Properties();
        pro.put("zookeeper.connect",Kafkaproperties.KAFKA_SERVER_URL+":"+Kafkaproperties.zkConnectPort);
        pro.put("group.id",Kafkaproperties.groupId);
        pro.put("zookeeper.session.timeout.ms","500");
        pro.put("zookeeper.sync.time.ms","200");
        pro.put("auto.commit.interval.ms","1000");
        consumer=kafka.consumer.Consumer.createJavaConsumerConnector(new ConsumerConfig(pro));
        this.topic=topic;
    }

    @Override
    public void run() {
        Map<String,Integer> topicCountMap=new HashMap<String, Integer>();
        topicCountMap.put(topic,new Integer(1));
        Map<String,List<KafkaStream<byte[],byte[]>>> consumerMap=consumer.createMessageStreams(topicCountMap);
        KafkaStream<byte[],byte[]> stream = consumerMap.get(topic).get(0);
        ConsumerIterator<byte[],byte[]> it = stream.iterator();
        while(it.hasNext()){
                  System.out.println(new String(it.next().message()));

                  //str=new String(it.next().message());

        }
    }
    public static void main(String[] args){
        Consumer con=new Consumer(Kafkaproperties.TOPIC);
        con.start();
    }
}
