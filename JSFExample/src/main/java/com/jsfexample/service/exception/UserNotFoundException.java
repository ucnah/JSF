package com.jsfexample.service.exception;

public class UserNotFoundException extends RuntimeException {

    public String getMessage(){
        return "Invalid credentials";
    }
}
