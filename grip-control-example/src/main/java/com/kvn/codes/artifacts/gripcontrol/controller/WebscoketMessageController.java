package com.kvn.codes.artifacts.gripcontrol.controller;

import com.kvn.codes.artifacts.gripcontrol.KafkaJsonSchemaSerDes;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import lombok.extern.slf4j.Slf4j;
import org.fanout.gripcontrol.GripControl;
import org.fanout.gripcontrol.WebSocketEvent;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@RestController
@RequestMapping("/backend")
@Slf4j
public class WebscoketMessageController {

    @PostMapping(path = "/events/{channel}", produces = "application/websocket-events")
    public ResponseEntity<String> ep(HttpServletResponse response, @PathVariable("channel") String channel, @RequestBody String content) throws UnsupportedEncodingException {
        log.info("content: {}", content);

        List<WebSocketEvent> inEvents = GripControl.decodeWebSocketEvents(content.getBytes());

        String wsEventType = inEvents.get(0).type;
        String contentV = inEvents.get(0).content;

        log.info("--- {}, {}", wsEventType, contentV);

        response.setHeader("Sec-WebSocket-Extensions", "grip");
//        response.setContentType("application/websocket-events");

        String result = "";
        if ("OPEN".equals(wsEventType)) {
            log.info("[open]");
            Map<String, Object> channelMap = new HashMap<String, Object>();
            channelMap.put("channel", channel + "#" +  UUID.randomUUID().toString() );
            List<WebSocketEvent> outEvents = new ArrayList<WebSocketEvent>();
            outEvents.add(new WebSocketEvent("OPEN"));
            outEvents.add(new WebSocketEvent("TEXT",
                    "c:" + GripControl.webSocketControlMessage("subscribe", channelMap)));
            result = GripControl.encodeWebSocketEvents(outEvents);
        } else if ("TEXT".equals(wsEventType)) {
            log.info("[text]");
            try {
                KafkaJsonSchemaSerDes.test(contentV);
            } catch (Exception e) {
                e.printStackTrace();
                List<WebSocketEvent> outEvents = new ArrayList<WebSocketEvent>();
                outEvents.add(new WebSocketEvent("CLOSE", "c:" + e.getMessage()));
                result = GripControl.encodeWebSocketEvents(outEvents);
            }
        }

        HttpHeaders headers = new HttpHeaders();
        //根据自己的需要动态添加你想要的content type
        headers.add(HttpHeaders.CONTENT_TYPE, "application/websocket-events");
        headers.add("Sec-WebSocket-Extensions", "grip");

        return new ResponseEntity<String>(result, headers, HttpStatus.OK);
    }
}
