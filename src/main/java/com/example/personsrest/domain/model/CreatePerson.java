package com.example.personsrest.domain.model;

import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
public class CreatePerson {

    @Size(min = 2, message = "Name must contain at least two characters")
    String name;
    @NotBlank(message = "City is mandatory")
    String city;
    @Min(value = 5, message = "Minimum age is 5")
    int age;
}
