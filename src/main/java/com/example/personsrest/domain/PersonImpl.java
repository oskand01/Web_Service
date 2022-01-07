package com.example.personsrest.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class PersonImpl implements Person {

    String id;
    String name;
    String city;
    int age;
    List<String> groups = new ArrayList<>();

    public PersonImpl(String name, String city, int age) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.city = city;
        this.age = age;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public int getAge() {
        return 0;
    }

    @Override
    public void setAge(int age) {

    }

    @Override
    public String getCity() {
        return null;
    }

    @Override
    public void setCity(String city) {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setActive(boolean active) {

    }

    @Override
    public List<String> getGroups() {
        return null;
    }

    @Override
    public void addGroup(String name) {
        this.groups.add(name);
    }

    @Override
    public void removeGroup(String groupId) {

    }
}
