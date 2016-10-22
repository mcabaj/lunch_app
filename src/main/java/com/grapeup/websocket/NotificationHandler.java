package com.grapeup.websocket;

import com.grapeup.domain.User;
import com.grapeup.web.security.AuthUserProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class NotificationHandler extends TextWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(NotificationHandler.class.getName());

    @Autowired
    private Broadcaster broadcaster;
    @Autowired
    private MessageExtractor messageExtractor;
    @Autowired
    private AuthUserProvider authUserProvider;
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
            session.sendMessage(new TextMessage("You are not authenticated"));
            return;
        }

        switch (messageType) {
            case AUTHENTICATION:
                User authUser = authUserProvider.getAuthUser(messageExtractor.getToken(message.getPayload()));
                if(authUser == null) {
                    session.sendMessage(new TextMessage("Invalid token"));
                    return;
                }
                clientConnection.setUsername(authUser.getUsername());
                clientConnection.authenticate();
                break;
            case CHAT_MSG:
                String msg = "new_message?msg="+messageExtractor.getChatMessage(message.getPayload())+"&sender="+clientConnection.getUsername()+"&time="+System.currentTimeMillis();
                broadcaster.broadcastMessage(msg);
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
