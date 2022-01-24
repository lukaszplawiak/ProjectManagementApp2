package com.lukaszplawiak.projectapp.exception;

public class IllegalCreateTaskException extends RuntimeException {
    private final String message;

    public IllegalCreateTaskException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
