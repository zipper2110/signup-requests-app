package com.github.zipper2110.something.messaging;

import java.util.UUID;

public class MessageId {
    private final String value;

    public MessageId(UUID value) {
        this.value = value.toString();
    }

    public String getValue() {
        return value;
    }
}
