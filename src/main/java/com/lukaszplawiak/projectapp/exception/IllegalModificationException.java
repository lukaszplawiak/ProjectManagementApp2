package com.lukaszplawiak.projectapp.exception;

public class IllegalModificationException extends RuntimeException {
    public IllegalModificationException(Long projectId) {
        super("Project of id: " + projectId + " is done. The action is impossible to execute");
    }
}