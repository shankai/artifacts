package com.kvn.codes.kafka.avro;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Instant;
import java.util.Properties;
import java.util.UUID;

public class AvroProducer {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "dev:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroSerializer");
        props.put("schema.registry.url", "http://dev:8081");

        KafkaProducer<String, User> producer = new KafkaProducer<>(props);

        while (true) {

            User user = new User("" + System.currentTimeMillis(), 12, "blue");

            System.out.println(System.currentTimeMillis() + "|Producer: " + user.toString());

            ProducerRecord<String, User> record = new ProducerRecord<>("kafka_connect_redis_topic_1", user);
            producer.send(record);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
        producer.close();

    }
}
