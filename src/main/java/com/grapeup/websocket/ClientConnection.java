package com.grapeup.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class ClientConnection {

    private static final Logger log = LoggerFactory.getLogger(ClientConnection.class.getName());

    private String connectionId;
    private WebSocketSession session;
    private boolean isAuthenticated;
    private String username;

    public ClientConnection(String connectionId, WebSocketSession session) {
        this.connectionId = connectionId;
        this.session = session;
        this.isAuthenticated = false;
    }

    public void sendMessage(String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            log.warn("Unable to broadcast message to session {}", session.getId());
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public void authenticate() {
        this.isAuthenticated = true;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }
}
