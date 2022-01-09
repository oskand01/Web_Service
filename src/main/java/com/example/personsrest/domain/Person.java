package com.example.personsrest.domain;

import java.util.List;
import java.util.Map;

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

    List<String> getGroupNames();


    void addGroup(String groupId);

    void setGroups(Map<String, String> groups);

    void removeGroup(String groupId);
}

