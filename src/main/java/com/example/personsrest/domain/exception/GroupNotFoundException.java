package com.example.personsrest.domain.exception;

public class GroupNotFoundException extends RuntimeException  {
    public GroupNotFoundException(String message) {
        super("Group '" + message + "' not found");
    }
}
