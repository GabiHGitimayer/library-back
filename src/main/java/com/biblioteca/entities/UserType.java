package com.biblioteca.entities;

public enum UserType {
    ADMIN("ADMIN"),
    EMPLOYEE("EMPLOYEE"),
    USER("USER");

    private String type;

    UserType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}