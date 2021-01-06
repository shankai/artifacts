package com.kvn.codes.artifacts.gripcontrol;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.confluent.kafka.schemaregistry.ParsedSchema;
import io.confluent.kafka.schemaregistry.avro.AvroSchemaProvider;
import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaMetadata;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import io.confluent.kafka.schemaregistry.json.JsonSchemaProvider;
import io.confluent.kafka.schemaregistry.json.JsonSchemaUtils;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializer;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

public class KafkaJsonSchemaSerDes {

    public static void test(String payloadStr) throws IOException, RestClientException {
        String schemaRegistryUrl = "http://icosevent-schemaregistry-service-icos.icos.icos.city/";
        SchemaRegistryClient client =

                new CachedSchemaRegistryClient(Arrays.asList(schemaRegistryUrl), 1000,
                        Arrays.asList(
//                                new ProtobufSchemaProvider(),
                                new AvroSchemaProvider(), new JsonSchemaProvider()), null);

        SchemaMetadata md = client.getLatestSchemaMetadata("EVENT.T2.json.t4-value");
        int schemaId = md.getId();
        System.out.println(schemaId);

        ParsedSchema ps = client.getSchemaById(md.getId());

//        ParsedSchema ps = client.getSchemaBySubjectAndId("EVENT.T2.json.t3-value", md.getId());

        System.out.println(ps.canonicalString());

        KafkaJsonSchemaSerializer serializer = new KafkaJsonSchemaSerializer(client);
        KafkaJsonSchemaDeserializer<Object> deserializer = new KafkaJsonSchemaDeserializer(client);

        String schemaStr = ps.toString();
//        String payloadStr = "222";
//        String payloadStr = "{\"id\":2,\"name\":\"An ice sculpture\",\"price\":12}";
//
        ObjectMapper mapper = new ObjectMapper();

        JsonNode jn = mapper.readTree(payloadStr.getBytes("UTF-8"));

        JSONObject joSchema = new JSONObject(schemaStr);
        JSONObject jo = new JSONObject(payloadStr);
        SchemaLoader loader = SchemaLoader.builder().schemaJson(joSchema).build();
        Schema schema = loader.load().build();
        schema.validate(jo);

        ObjectNode result = JsonSchemaUtils.envelope(mapper.readTree(schemaStr), jn);
//
        byte[] byteResult = serializer.serialize("EVENT.T2.json.t4", result);

        System.out.println(byteResult);
        Object deResult = deserializer.deserialize(null, byteResult);
        System.out.println(deResult);
    }
}
