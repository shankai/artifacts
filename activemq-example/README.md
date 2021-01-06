# ActiveMQ 客户端简单应用示例

## MQTT (MQTT: 1883)

Example: `me.kvn.codes.activemq.mqtt`

## JMS (TCP: 61616)

https://javaee.github.io/glassfish/doc/4.0/mq-tech-over.pdf
https://javaee.github.io/glassfish/doc/4.0/mq-dev-guide-java.pdf

- Queue: 多个订阅者轮询接收到发布者发布的消息。消息的接受顺序并不一定要与消息的发送顺序相同。一旦一个消息被阅读，该消息将被从队列中移走。
- Topic: 多个订阅者分别接收到发布者发布的消息。

Example: `me.kvn.codes.activemq.jms`

## AMQP

https://activemq.apache.org/amqp
https://github.com/apache/qpid-jms

Example: `org.apache.qpid.jms.amqp`
- HelloWorld producer&consumer
- Sender & Receiver
- Server & Client (temp queue)

## STOMP (STOMP: 61613)

Example: `me.kvn.codes.activemq.stomp`