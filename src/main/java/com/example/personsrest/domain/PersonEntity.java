package com.example.personsrest.domain;

import org.springframework.data.annotation.Id;

import java.util.List;

public class PersonEntity {

    String id;
    String name;
    String city;
    int age;
    List<String> groups;


}
