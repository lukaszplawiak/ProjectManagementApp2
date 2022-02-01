package com.lukaszplawiak.projectapp.controller;

import com.lukaszplawiak.projectapp.exception.*;
import com.lukaszplawiak.projectapp.exception.IllegalAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class IllegalExceptionControllerAdvice {
    //@ResponseBody?
    @ExceptionHandler(IllegalAccessException.class)
    ResponseEntity<?> handleIllegalAccessException(IllegalAccessException e) {
        return ResponseEntity.status(403).build();
    }
    @ExceptionHandler(IllegalActionTaskException.class)
    ResponseEntity<?> handleIllegalActionTaskException(IllegalActionTaskException e) {
        return ResponseEntity.status(403).build();
    }
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(400).build();
    }
    @ExceptionHandler(IllegalCreateTaskException.class)
    ResponseEntity<?> handleIllegalCreateTaskException(IllegalCreateTaskException e) {
        return ResponseEntity.status(409).build();
    }
    @ExceptionHandler(IllegalInputException.class)
    ResponseEntity<?> handleIllegalInputException(IllegalInputException e) {
        return ResponseEntity.status(400).build();
    }
    }
