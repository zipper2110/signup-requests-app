package com.github.zipper2110.something.entity;


import javax.validation.constraints.NotBlank;

public class Username {
    @NotBlank
    private final String value;

    public Username(@NotBlank String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}