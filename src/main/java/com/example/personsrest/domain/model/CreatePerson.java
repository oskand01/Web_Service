package com.example.personsrest.domain.model;

import lombok.Value;

@Value
public class CreatePerson {
    String name;
    String city;
    int age;
}
