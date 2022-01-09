package com.example.personsrest;

import com.example.personsrest.domain.*;
import com.example.personsrest.remote.GroupRemote;
import com.example.personsrest.remote.GroupRemoteImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Config {

    @Bean
    public GroupRemote groupRemote() {
        return new GroupRemoteImpl();
    }

    @Bean
    public PersonRepository personRepository() {
        return new PersonRepositoryImpl();
    }

}
