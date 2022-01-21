package com.example.personsrest.domain.exception;

public class PersonNotFoundException extends RuntimeException  {
    public PersonNotFoundException(String message) {
        super("Person id '" + message + "' not found");
    }
}
