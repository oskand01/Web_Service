package com.example.personsrest.domain;

public class PersonNotFoundException extends Exception {
    public PersonNotFoundException(String id) {
        super(id);
    }
}
