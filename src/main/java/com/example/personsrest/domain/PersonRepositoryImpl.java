package com.example.personsrest.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class PersonRepositoryImpl implements PersonRepository {

    Map<String, PersonImpl> persons = new HashMap<>();


    @Override
    public Optional<Person> findById(String id) {
        return null;
    }

    @Override
    public List<Person> findAll() {
        return null;
    }

    @Override
    public Page<Person> findAllByNameContainingOrCityContaining(String name, String city, Pageable pageable) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public PersonImpl save(Person person) {
        log.info(person.getName());
        PersonImpl person1 = new PersonImpl(person.getName(), person.getCity(), person.getAge());
        persons.put(person1.id, person1);
        return person1;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public String value() {
        return null;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
