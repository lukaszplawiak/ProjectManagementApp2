package com.lukaszplawiak.projectapp.exception;

public class IllegalActionException extends RuntimeException {
    public IllegalActionException(Long projectId) {
        super("Project of id: " + projectId + " is done. The action is impossible to execute");
    }
}