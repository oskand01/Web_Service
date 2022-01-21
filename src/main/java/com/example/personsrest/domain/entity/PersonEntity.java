package com.example.personsrest.domain.entity;

import com.example.personsrest.remote.GroupRemote;
import com.example.personsrest.remote.GroupRemoteImpl;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

import java.util.*;

@Data
@Entity
@Table(name = "person")
@NoArgsConstructor
public class PersonEntity implements Person {

    @Id
    String id;
    String name;
    String city;
    int age;
    @ElementCollection
    List<String> groups = new ArrayList<>();


    public PersonEntity(String name, String city, int age) {
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
        GroupRemote groupRemote = new GroupRemoteImpl();

        //Removes the group from the group API, maybe not needed?
        groupRemote.removeGroup(groupId);
        groups.remove(groupId);
    }
}
