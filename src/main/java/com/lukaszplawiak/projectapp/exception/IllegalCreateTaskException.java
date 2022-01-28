package com.lukaszplawiak.projectapp.exception;

public class IllegalCreateTaskException extends RuntimeException {

    public IllegalCreateTaskException(Long projectId) {
        super("Project of id: " + projectId + " is done. Create task is impossible");
    }
}
