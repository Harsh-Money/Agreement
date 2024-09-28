package com.app.agreement.configurations;

import com.app.agreement.entity.AgreementEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CustomWebSocketHandler implements WebSocketHandler {

    private static final Set<WebSocketSession> socketSessions = Collections.synchronizedSet(new HashSet<>());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        socketSessions.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        session.close();
        socketSessions.remove(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        socketSessions.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public void broadcastMessage(List<AgreementEntity> message) {
        synchronized (socketSessions) {
            for (WebSocketSession session : socketSessions) {
                if (session.isOpen()) {
                    try {
                        String convertMessageToJSON = objectMapper.writeValueAsString(message);
                        session.sendMessage(new TextMessage(convertMessageToJSON)) ;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
