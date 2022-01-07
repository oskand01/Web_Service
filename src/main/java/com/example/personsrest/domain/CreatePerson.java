package com.example.personsrest.domain;

import lombok.Value;

@Value
public class CreatePerson {
    String name;
    String city;
    int age;
}
