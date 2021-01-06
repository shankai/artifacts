package com.kvn.codes.websocket.client;

import org.java_websocket.WebSocket;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class DevPushpinExample {

    public static void main(String[] args) {
        try {

            for(int i= 0; i< 30; i++) {
                int finalI = i;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        Map<String, String> headers = new HashMap<>();
//                        headers.put("icos-Provider-Type", "redis");
                        MyWebSocketClient client = null;
                        try {
                            client = new MyWebSocketClient("ws://dev:7999/backend/events/r" + finalI);
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        client.connect();
                        while (!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
                            System.out.println("还没有打开");
                        }
                        System.out.println("建立websocket连接");
                    }
                }).start();

            }

            Map<String, String> headers = new HashMap<>();
            headers.put("icos-Provider-Type", "redis");
            MyWebSocketClient client = new MyWebSocketClient("ws://icosevent-icmseventpushpinx-service-xadit.xadit.icos.city/event/r2", headers);
            client.connect();
            while (!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
                System.out.println("还没有打开");
            }
            System.out.println("建立websocket连接");
//            client.send("asd");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
