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

/**
 * //@todo class description
 *
 * @author mcabaj
 */
@Component
public class MessageExtractor {

    public MessageType getMessageType(String message) {
        String typeName = message.split("\\?")[0];
        MessageType messageType = MessageType.getByMessageTypeName(typeName);

        return messageType;
    }

    public String getChatMessage(String message) {
        String params = message.split("\\?")[1];
        String[] split = params.split("=");
        String key = split[0];
        if("msg".equals(key)) {
            return split[1];
        } else {
            return "";
        }
    }
}
