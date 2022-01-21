package com.example.personsrest.domain.entity;

import java.util.List;

public interface Person {
    String getId();

    String getName();

    void setName(String name);

    int getAge();

    void setAge(int age);

    String getCity();

    void setCity(String city);

    boolean isActive();

    void setActive(boolean active);

    List<String> getGroups();

    void addGroup(String groupId);

    void removeGroup(String groupId);
}

