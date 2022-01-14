package com.example.personsrest.domain.exception;

public class PersonNotFoundException extends Exception {
    public PersonNotFoundException(String message) {
        super("No person with id '" + message + "' found");
    }
}
