/*
 * Avaya Inc. – Proprietary (Restricted)
 * Solely for authorized persons having a need to know
 * pursuant to Company instructions.
 *
 * Copyright © 2006-2014 Avaya Inc. All rights reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Avaya Inc.
 * The copyright notice above does not evidence any actual
 * or intended publication of such source code.
 */
package com.grapeup.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mcabaj
 */
@Component
public class Broadcaster {

    private Map<String, WebSocketSession> webSocketConnections;

    public Broadcaster() {
        webSocketConnections = new HashMap<>();
    }

    public void addConnection(WebSocketSession session) {
        webSocketConnections.put(session.getId(), session);
    }

    public void removeConnection(WebSocketSession session) {
        webSocketConnections.remove(session.getId());
    }

    public void broadcastMessage(String message) {
        for(WebSocketSession session : webSocketConnections.values()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                // add logs
            }
        }
    }
}
