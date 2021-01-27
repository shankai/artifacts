package com.kvn.codes.kafka.protobuf;

import com.kvn.codes.kafka.protobuf.AddressBookProtos.Person;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.Properties;

public class ProtobufProducer {

    public static void main(String[] args) throws IOException {

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "dev:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaProtobufSerializer.class);
        props.put("schema.registry.url", "http://dev:8081");

        KafkaProducer<String, Person> producer = new KafkaProducer<>(props);

        while (true) {

            Person john = Person.newBuilder().setId(1234).setEmail("a1-----------------@email.com").setName("a1").build();
            System.out.println(john.toString());

            System.out.println(System.currentTimeMillis() + "|Producer: " + john.toString());

            ProducerRecord<String, Person> record = new ProducerRecord<>("kafka_connect_redis_topic_2", john);
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
