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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author mcabaj
 */
public class NotificationHandler extends TextWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(NotificationHandler.class.getName());

    @Autowired
    private Broadcaster broadcaster;
    @Autowired
    private MessageTypeResolver messageTypeResolver;
    private ClientConnection clientConnection;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        clientConnection = new ClientConnection(session.getId(), session);
        broadcaster.addConnection(clientConnection);
        log.info("Session {0} added to broadcast clients", session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        MessageType messageType = messageTypeResolver.getMessageType(message.getPayload());
        if (!isConnectionAuthenticated(session) && !isAuthenticationMessage(message)) {
            return;
        }

        switch (messageType) {
            case AUTHENTICATION:
                // extract token and authenticate user (assign login and groupId to ClientConnection)
                break;
            case CHAT_MSG:
                broadcaster.broadcastMessage(message.getPayload());
                break;
        }

        log.info("Message received from session {0} : {1} ", session.getId(), message);
    }

    private boolean isAuthenticationMessage(TextMessage message) {
        return MessageType.AUTHENTICATION.equals(message);
    }

    private boolean isConnectionAuthenticated(WebSocketSession session) {
        return broadcaster.getConnection(session.getId()).isAuthenticated();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        broadcaster.removeConnection(session.getId());
        log.info("Session {0} removed from broadcast clients", session.getId());
    }
}
