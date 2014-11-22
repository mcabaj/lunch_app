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

/**
 * @author mcabaj
 */
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
