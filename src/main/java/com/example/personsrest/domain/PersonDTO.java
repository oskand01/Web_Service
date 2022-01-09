package com.example.personsrest.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class PersonDTO {
    String id;
    String name;
    String city;
    int age;
    List<String> groups;

    @JsonCreator
    public PersonDTO(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("city") String city,
            @JsonProperty("age") int age,
            @JsonProperty("groups") List<String> groups) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.age = age;
        this.groups = groups;
    }


}
