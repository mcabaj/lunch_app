package com.grapeup.websocket;

public enum MessageType {
    AUTHENTICATION("authenticate"),
    CHAT_MSG("send_message"),
    UNKNOWN("unknown");

    private String messageType;

    private MessageType(final String messageType) {
        this.messageType = messageType;
    }

    public static MessageType getByMessageTypeName(final String messageTypeName) {
        if (messageTypeName != null) {
            for (MessageType messageType : values()) {
                if (messageType.messageType.equalsIgnoreCase(messageTypeName)) {
                    return messageType;
                }
            }
            return UNKNOWN;
        }
        throw new IllegalArgumentException("MessageType not found: " + messageTypeName);
    }

    public String getMessageType() {
        return messageType;
    }
}
