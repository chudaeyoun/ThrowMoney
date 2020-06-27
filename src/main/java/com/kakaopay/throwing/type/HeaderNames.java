package com.kakaopay.throwing.type;

public enum HeaderNames {
    USER("X-USER-ID"),
    ROOM("X-ROOM-ID");

    private String name;

    HeaderNames(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
