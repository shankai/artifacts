package me.kvn.codes.activemq.stomp;

import org.apache.activemq.transport.stomp.Stomp;
import org.apache.activemq.transport.stomp.StompConnection;
import java.net.Socket;

public class StompSender {
    public static void main(String[] args) {
        send();
    }

    public static void send() {
        int count = 0;
        StompConnection stompConnection = new StompConnection();
        try {
            Socket socket = new Socket(StompServerConstants.STOMP_SERVER_HOST, StompServerConstants.STOMP_SERVER_PORT);
            stompConnection.open(socket);
            stompConnection.setVersion(Stomp.V1_2);
            stompConnection.connect(StompServerConstants.STOMP_SERVER_USERNAME, StompServerConstants.STOMP_SERVER_PASSWORD);
            while (true) {
                System.out.println("Sending Message");
                stompConnection.send(StompServerConstants.STOMP_SERVER_DESTINATION, "StompMessage:" + (++count));
                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stompConnection.disconnect();
                stompConnection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
