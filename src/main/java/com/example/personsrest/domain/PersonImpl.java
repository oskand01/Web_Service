package com.example.personsrest.domain;

import com.example.personsrest.Config;
import com.example.personsrest.remote.GroupRemote;
import com.example.personsrest.remote.GroupRemoteImpl;
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
    Map<String, String> groups = new HashMap<>();

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
        return new ArrayList<>(groups.values());
    }


    @Override
    public void setGroups(Map<String, String> groups) {
        this.groups = groups;
    }

    @Override
    public void addGroup(String groupId) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        GroupRemote groupRemote = context.getBean(GroupRemote.class);

        String name = groupRemote.getNameById(groupId);

        if (!groups.containsKey(groupId)) {
            groups.put(groupId, name);
            log.info("\n\ngroupId: " + groupId + "\ngroupName: " + name + "\n");
        }
    }

    @Override
    public void removeGroup(String groupId) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        GroupRemote groupRemote = context.getBean(GroupRemote.class);

        groupRemote.removeGroup(groupId);
        groups.remove(groupId);
    }
}
