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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * //@todo class description
 *
 * @author mcabaj
 */
public class ClientConnection {

    private static final Logger log = LoggerFactory.getLogger(ClientConnection.class.getName());

    private String connectionId;
    private WebSocketSession session;
    private boolean isAuthenticated;

    public ClientConnection(String connectionId, WebSocketSession session) {
        this.connectionId = connectionId;
        this.session = session;
        this.isAuthenticated = false;
    }

    public void sendMessage(String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            log.warn("Unable to broadcast message to session {0}", session.getId());
        }
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
