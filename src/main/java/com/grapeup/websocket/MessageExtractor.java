package com.grapeup.websocket;

import org.springframework.stereotype.Component;

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

    public String getToken(String message) {
        String params = message.split("\\?")[1];
        String[] split = params.split("=");
        String key = split[0];
        if("token".equals(key)) {
            return split[1];
        } else {
            return "";
        }
    }

}
