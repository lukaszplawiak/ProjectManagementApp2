package com.lukaszplawiak.projectapp.exception;

public class IllegalAccessException extends RuntimeException {

    public IllegalAccessException() {
        super("Update access denied");
    }
}
