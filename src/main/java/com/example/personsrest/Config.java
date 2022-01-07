package com.example.personsrest;

import com.example.personsrest.domain.PersonRepository;
import com.example.personsrest.domain.PersonRepositoryImpl;
import com.example.personsrest.remote.GroupRemote;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public GroupRemote groupRemote() {
        return null;
    }

    @Bean
    public PersonRepository personRepository() {
        return new PersonRepositoryImpl();
    }
}
