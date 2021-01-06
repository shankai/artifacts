package com.kvn.codes.kafka.avro;

import org.apache.kafka.clients.consumer.*;

import java.util.Arrays;
import java.util.Properties;

public class AvroConsumer {

    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.15.6.118:19092");
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroDeserializer");
        prop.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group-11");
        prop.put("schema.registry.url", "http://icosevent-schemaregistry-service-icos.icos.icos.city");
        prop.put("enable.auto.commit", "false");
        prop.put("auto.commit.interval.ms", "1000");
        prop.put("auto.offset.reset", "latest");
        prop.put("specific.avro.reader", "true");

        Consumer<String, User> consumer = new KafkaConsumer<>(prop);
        consumer.subscribe(Arrays.asList("EVENT.t1.avro.value"));
        while (true) {
            try {
                ConsumerRecords<String, User> records = consumer.poll(100);
                for (ConsumerRecord<String, User> record : records) {
                    User u = record.value();
                    System.out.println("Consumer: " + u.toString());
                }

            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        consumer.close();
    }

}
