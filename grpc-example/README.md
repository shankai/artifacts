# gRPC example

```shell
mvn clean package
```

## Service(Java)

grpc-service: service 与 message 定义 (protobuf)

## Server(Java)

grpc-server: server listening on 50051 (VehicleServer)

## Client(Java)

grpc-client: client call (VehicleClient)

## Client(Nodejs)

grpc-client-nodejs: client call

```shell
cd grpc-client-nodejs
npm install
node vehicle-client.js
```