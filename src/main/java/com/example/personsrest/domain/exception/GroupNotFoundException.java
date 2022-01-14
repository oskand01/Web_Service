package com.example.personsrest.domain.exception;

public class GroupNotFoundException extends Exception {
    public GroupNotFoundException(String message) {
        super("No group with id '" + message + "' found");
    }
}
