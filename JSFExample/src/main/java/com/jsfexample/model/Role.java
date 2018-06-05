package com.jsfexample.model;

public enum Role {

    STUDENT("Student"), ADMINISTRATOR("Administrator");

    private String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
