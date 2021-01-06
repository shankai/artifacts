package com.kvn.codes.websocket.client;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Map;

public class MyWebSocketClient extends WebSocketClient {

    public MyWebSocketClient(String url) throws URISyntaxException {
        super(new URI(url));
    }

    public MyWebSocketClient(String url, Map<String, String> headers) throws URISyntaxException {
        super(new URI(url), new Draft_6455(), headers, 0);
    }

    @Override
    public void onOpen(ServerHandshake shake) {
        System.out.println("握手...");
        for (Iterator<String> it = shake.iterateHttpFields(); it.hasNext(); ) {
            String key = it.next();
            System.out.println(key + ":" + shake.getFieldValue(key));
        }
    }

    @Override
    public void onMessage(String paramString) {
        System.out.println("接收到消息：" + paramString);
    }

    @Override
    public void onClose(int paramInt, String paramString, boolean paramBoolean) {
        System.out.println("关闭...");
    }

    @Override
    public void onError(Exception e) {
        System.out.println("异常" + e);

    }

    @Override
    public void onWebsocketPing(WebSocket conn, Framedata f) {
        System.out.println("ping");
        super.onWebsocketPing(conn, f);
    }

    @Override
    public void onWebsocketPong(WebSocket conn, Framedata f) {
        System.out.println("pong");
        super.onWebsocketPong(conn, f);
    }
}
