package com.lukaszplawiak.projectapp.exception;

public class IllegalAccessException extends RuntimeException{
    private final String message;

    public IllegalAccessException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
