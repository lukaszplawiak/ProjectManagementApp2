package com.lukaszplawiak.projectapp.exception;

public class IllegalActionTaskException extends RuntimeException {
    public IllegalActionTaskException(Long projectId) {
        super("Project of id: " + projectId + " is done. Toggle task is impossible");
    }
}
