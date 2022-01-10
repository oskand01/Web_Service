package com.example.personsrest.domain.model;

import lombok.Value;

@Value
public class UpdatePerson {
    String name;
    String city;
    int age;
}
