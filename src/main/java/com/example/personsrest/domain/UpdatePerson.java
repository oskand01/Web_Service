package com.example.personsrest.domain;

import lombok.Value;

@Value
public class UpdatePerson {
    String name;
    String city;
    int age;
}
