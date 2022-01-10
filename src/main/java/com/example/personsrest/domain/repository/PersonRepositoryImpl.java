package com.example.personsrest.domain.repository;

import com.example.personsrest.domain.Person;
import com.example.personsrest.domain.PersonImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class PersonRepositoryImpl implements PersonRepository {

    Map<String, Person> persons = new HashMap<>();

    @Override
    public Optional<Person> findById(String id) {
        return Optional.of(persons.get(id));
    }

    @Override
    public List<Person> findAll() {
        return new ArrayList<>(persons.values());
    }

    @Override
    public Page<Person> findAllByNameContainingOrCityContaining(String name, String city, Pageable pageable) {
        List<Person> list =
                persons.values().stream()
                        .filter(person -> person.getName().equalsIgnoreCase(name) || person.getCity().equalsIgnoreCase(city))
                        .collect(Collectors.toList());
        return new PageImpl<>(list);    }


    @Override
    public void deleteAll() {

    }

    @Override
    public Person save(Person person) {
        persons.put(person.getId(), person);
        return person;
    }

    @Override
    public void delete(String id) {
        persons.remove(id);
    }

}
