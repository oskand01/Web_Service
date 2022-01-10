package com.example.personsrest.domain.exception;

public class PersonNotFoundException extends Exception {
    public PersonNotFoundException(String id) {
        super(id);
    }
}
