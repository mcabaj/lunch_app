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
public class MessageTypeResolver {

    public MessageType getMessageType(String message) {
        String typeName = message.split("\\?")[0];
        MessageType messageType = MessageType.getByMessageTypeName(typeName);

        return messageType;
    }
}
