package com.github.zipper2110.something.messaging;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class MessageId {
    private @NotNull final String value;

    public MessageId(@NotNull UUID value) {
        this.value = value.toString();
    }

    public @NotNull String getValue() {
        return value;
    }
}
