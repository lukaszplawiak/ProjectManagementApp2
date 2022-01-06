package com.lukaszplawiak.projectapp.controller;

import com.lukaszplawiak.projectapp.exception.IllegalCreateTaskException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class IllegalExceptionControllerAdvice {
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalCreateTaskException.class)
    ResponseEntity<?> handleIllegalCreateTask(IllegalCreateTaskException e) {
        return ResponseEntity.badRequest().build();
    }
}
