package com.github.zipper2110.something.entity;

import javax.validation.constraints.NotBlank;

public class EmailAddress {
    @NotBlank
    private final String value;

    public EmailAddress(@NotBlank String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}