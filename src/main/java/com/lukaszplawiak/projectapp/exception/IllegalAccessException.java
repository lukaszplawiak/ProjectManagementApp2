package com.lukaszplawiak.projectapp.exception;

public class IllegalAccessException extends RuntimeException {
    public IllegalAccessException() {
        super("Access denied");
    }
}