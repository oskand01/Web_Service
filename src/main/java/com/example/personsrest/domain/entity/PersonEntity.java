package com.example.personsrest.domain.entity;

import com.example.personsrest.remote.GroupRemote;
import com.example.personsrest.remote.GroupRemoteImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.*;

@Data
@Entity
@Table(name = "person")
@NoArgsConstructor
@AllArgsConstructor
public class PersonEntity implements Person {

    @Id
    String id;
    String name;
    String city;
    int age;
    @ElementCollection
    @CollectionTable(name = "person_groups", joinColumns = @JoinColumn(name = "person_id"))
    @Column(name = "group_id")
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

        groupRemote.removeGroup(groupId);
        groups.remove(groupId);
    }
}
