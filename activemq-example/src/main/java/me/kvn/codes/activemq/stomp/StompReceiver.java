package me.kvn.codes.activemq.stomp;

import org.apache.activemq.transport.stomp.Stomp;
import org.apache.activemq.transport.stomp.StompConnection;
import org.apache.activemq.transport.stomp.StompFrame;

import java.net.Socket;

public class StompReceiver {
    public static void main(String[] args) {
        receive();
    }

    public static void receive() {
        StompConnection stompConnection = new StompConnection();
        try {
            Socket socket = new Socket(StompServerConstants.STOMP_SERVER_HOST, StompServerConstants.STOMP_SERVER_PORT);
            stompConnection.open(socket);
            stompConnection.setVersion(Stomp.V1_2);
            stompConnection.connect(StompServerConstants.STOMP_SERVER_USERNAME, StompServerConstants.STOMP_SERVER_PASSWORD);
            stompConnection.subscribe(StompServerConstants.STOMP_SERVER_DESTINATION);
            while (true) {
                // timeout default 10000L, 0  infinite
                StompFrame frame = stompConnection.receive(0);
                System.out.println(frame);
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
