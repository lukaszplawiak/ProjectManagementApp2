package com.lukaszplawiak.projectapp.exception;

public class IllegalInputException extends RuntimeException {
    public IllegalInputException() {
        super("Illegal input data");
    }
}
