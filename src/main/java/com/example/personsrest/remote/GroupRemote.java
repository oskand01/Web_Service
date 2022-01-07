package com.example.personsrest.remote;


public interface GroupRemote {

    String getNameById(String groupId);

    String createGroup(String name);

    String removeGroup(String name);
}
