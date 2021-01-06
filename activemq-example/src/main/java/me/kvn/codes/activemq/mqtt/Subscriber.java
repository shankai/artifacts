package me.kvn.codes.activemq.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * MQTT 数据订阅
 */
public class Subscriber {

    public static void main(String[] args) {

        String clientId = "JavaSample123";
        MemoryPersistence persistence = new MemoryPersistence();

        MqttClient sampleClient = null;
        try {
            sampleClient = new MqttClient(MQTTConstants.BROKER, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: " + MQTTConstants.BROKER);
            sampleClient.connect(connOpts);
            System.out.println("Connected");

            sampleClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                    System.err.println(String.format("Conn Lost %s", throwable.getMessage()));
                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                    String fmt = String.format("MessageArrived: %s, %s", s, mqttMessage.toString());
                    System.out.println(fmt);
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                }
            });

            sampleClient.subscribe(MQTTConstants.TOPIC, MQTTConstants.QOS);

        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        } finally {
//            try {
//                sampleClient.disconnect();
//            } catch (MqttException e) {
//                e.printStackTrace();
//            }
//            System.out.println("Disconnected");
        }
    }
}
