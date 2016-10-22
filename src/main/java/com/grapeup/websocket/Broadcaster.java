package com.grapeup.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Broadcaster {

    private static final Logger log = LoggerFactory.getLogger(Broadcaster.class.getName());

    private Map<String, ClientConnection> clientConnections;    // connectionId -> ClientConnection

    public Broadcaster() {
        clientConnections = new HashMap<>();
    }

    public void addConnection(ClientConnection clientConnection) {
        clientConnections.put(clientConnection.getConnectionId(), clientConnection);
    }

    public void removeConnection(String connectionId) {
        clientConnections.remove(connectionId);
    }

    public ClientConnection getConnection(String connectionId) {
        return clientConnections.get(connectionId);
    }

    public void broadcastMessage(String message) {
        for (ClientConnection connection : clientConnections.values()) {
            if(connection.isAuthenticated()) {
                connection.sendMessage(message);
            }
        }
    }
}
