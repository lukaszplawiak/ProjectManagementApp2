package com.lukaszplawiak.projectapp.exception;


public class IllegalCreateTaskException extends RuntimeException {
    private String message;

    public IllegalCreateTaskException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
