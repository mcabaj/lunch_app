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
    private MessageExtractor messageExtractor;
    private ClientConnection clientConnection;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        clientConnection = new ClientConnection(session.getId(), session);
        broadcaster.addConnection(clientConnection);
        log.info("Session {} added to broadcast clients", session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        MessageType messageType = messageExtractor.getMessageType(message.getPayload());
        if (!isConnectionAuthenticated(session) && !isAuthenticationMessage(messageType)) {
            return;
        }

        switch (messageType) {
            case AUTHENTICATION:
                clientConnection.authenticate();
                // extract token and authenticate user (assign login and groupId to ClientConnection)
                break;
            case CHAT_MSG:
                broadcaster.broadcastMessage(messageExtractor.getChatMessage(message.getPayload()));
                break;
        }

        log.info("Message received from session {} : {} ", session.getId(), message);
    }

    private boolean isAuthenticationMessage(MessageType type) {
        return MessageType.AUTHENTICATION.equals(type);
    }

    private boolean isConnectionAuthenticated(WebSocketSession session) {
        return broadcaster.getConnection(session.getId()).isAuthenticated();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        broadcaster.removeConnection(session.getId());
        log.info("Session {} removed from broadcast clients", session.getId());
    }
}
