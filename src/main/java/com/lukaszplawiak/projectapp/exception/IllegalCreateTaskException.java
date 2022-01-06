package com.lukaszplawiak.projectapp.exception;

import org.springframework.http.HttpStatus;

public class IllegalCreateTaskException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public IllegalCreateTaskException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
