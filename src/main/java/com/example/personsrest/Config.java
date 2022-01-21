package com.example.personsrest;

import com.example.personsrest.domain.repository.PersonDBRepository;
import com.example.personsrest.domain.repository.PersonRepository;
import com.example.personsrest.domain.repository.PersonRepositoryImpl;
import com.example.personsrest.remote.GroupRemote;
import com.example.personsrest.remote.GroupRemoteImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Config {

    @Bean
    public GroupRemote groupRemote() {
        return new GroupRemoteImpl();
    }

    @Bean
    public PersonRepository personRepository(PersonDBRepository personDBRepository) {
        return new PersonRepositoryImpl(personDBRepository);
    }

}
