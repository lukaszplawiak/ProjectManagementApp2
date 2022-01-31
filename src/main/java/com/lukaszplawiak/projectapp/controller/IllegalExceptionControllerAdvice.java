package com.lukaszplawiak.projectapp.controller;

import com.lukaszplawiak.projectapp.exception.IllegalCreateTaskException;
import com.lukaszplawiak.projectapp.exception.IllegalAccessException;
import com.lukaszplawiak.projectapp.exception.IllegalInputException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class IllegalExceptionControllerAdvice {
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(IllegalCreateTaskException.class)
    ResponseEntity<?> handleIllegalCreateTask(IllegalCreateTaskException e) {
        return ResponseEntity.status(409).build();
    }

    //@ResponseBody ?
    @ExceptionHandler(IllegalAccessException.class)
    ResponseEntity<?> handleIllegalUpdateAccess(IllegalAccessException e) {
        return ResponseEntity.status(403).build();
    }

    @ExceptionHandler(IllegalInputException.class)
    ResponseEntity<?> handleIllegalInputException(IllegalAccessException e) {
        return ResponseEntity.status(400).build();
    }


}
