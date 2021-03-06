package com.lukaszplawiak.projectapp.controller;

import com.lukaszplawiak.projectapp.exception.IllegalAccessException;
import com.lukaszplawiak.projectapp.exception.IllegalInputException;
import com.lukaszplawiak.projectapp.exception.IllegalModificationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class ExceptionControllerAdvice {
    @ExceptionHandler(IllegalAccessException.class)
    ResponseEntity<?> handleIllegalAccessException(IllegalAccessException e) {
        return ResponseEntity.status(403).build();
    }
    @ExceptionHandler(IllegalModificationException.class)
    ResponseEntity<?> handleIllegalActionTaskException(IllegalModificationException e) {
        return ResponseEntity.status(409).build();
    }
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(400).build();
    }
    @ExceptionHandler(IllegalInputException.class)
    ResponseEntity<?> handleIllegalInputException(IllegalInputException e) {
        return ResponseEntity.status(400).build();
    }
}