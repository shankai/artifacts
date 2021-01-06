package me.kvn.codes.activemq.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * MQTT 数据发布
 */
public class Sensor {

    public static void main(String[] args) {
        String clientId = "JavaSample";
        MemoryPersistence persistence = new MemoryPersistence();

        MqttClient sampleClient = null;
        try {
            sampleClient = new MqttClient(MQTTConstants.BROKER, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            sampleClient.connect(connOpts);

            System.out.println(String.format("Connected: %s", MQTTConstants.BROKER));

            for (int i = 0; i < 1000; i++) {
                String msg = String.format("%s%s%d", MQTTConstants.CONTENT, " - ", i);
                MqttMessage message = new MqttMessage(msg.getBytes());
                message.setQos(MQTTConstants.QOS);

                sampleClient.publish(MQTTConstants.TOPIC, message);

                Thread.sleep(100);
            }

        } catch (MqttException | InterruptedException me) {
            me.printStackTrace();
        } finally {
            try {
                sampleClient.disconnect();
            } catch (MqttException e) {
                e.printStackTrace();
            }
            System.exit(0);
            System.out.println("Disconnected");
        }
    }
}
