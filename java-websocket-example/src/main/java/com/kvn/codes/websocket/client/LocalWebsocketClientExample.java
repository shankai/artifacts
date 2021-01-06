package com.kvn.codes.websocket.client;

import org.java_websocket.WebSocket;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class LocalWebsocketClientExample {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            new Thread(new Runnable() {

                @Override
                public void run() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("icos-Provider-Type", "redis");
                    MyWebSocketClient client = null;
                    try {
                        client = new MyWebSocketClient("ws://dev:7999/ka/events/mychannel" + finalI, headers);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    client.connect();
                    System.out.println(finalI);
                    while (!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
                            System.out.println("还没有打开");
                    }
                    System.out.println("建立websocket连接");
                }
            }).start();

        }

    }

}
