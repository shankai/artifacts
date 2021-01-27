# Kafka Connect Client Example

## Avro

Avro Schema: `user.avsc`

main class
```
com.kvn.codes.kafka.avro.AvroProducer
```


## Protobuf

build
```
cd src/main/protobuf/
protoc --java_out=../java/ addressbook.proto
```

main class
```
com.kvn.codes.kafka.protobuf.ProtobufProducer
```