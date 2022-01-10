package com.example.personsrest.domain;

import com.example.personsrest.Config;
import com.example.personsrest.remote.GroupRemote;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.*;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    public boolean isActive() {
        return false;
    }

    @Override
    public void setActive(boolean active) {

    }

    @Override
    public List<String> getGroups() {
        return groups;
    }

    @Override
    public void addGroup(String groupId) {
        groups.add(groupId);
    }

    @Override
    public void removeGroup(String groupId) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        GroupRemote groupRemote = context.getBean(GroupRemote.class);

        //Removes the group from the group API, maybe not needed?
        groupRemote.removeGroup(groupId);

        groups.remove(groupId);
    }
}
