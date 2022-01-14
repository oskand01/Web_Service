package com.example.personsrest.domain.repository;

import com.example.personsrest.domain.Person;
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
        return Optional.ofNullable(persons.get(id));
    }

    @Override
    public List<Person> findAll() {
        return new ArrayList<>(persons.values());
    }

    @Override
    public Page<Person> findAllByNameContainingOrCityContaining(String name, String city, Pageable pageable) {

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int size = persons.size();

        int pages = size / pageSize;
        List<Person> filtered = null;

        if (pages < 1) {
            if (name.equals("") && (city.equals(""))) {
                filtered = persons.values().stream()
                        .sorted(Comparator.comparing(Person::getName))
                        .collect(Collectors.toList());
            } else {
                filtered = persons.values().stream()
                        .filter(person -> person.getName().equalsIgnoreCase(name) || person.getCity().equalsIgnoreCase(city))
                        .sorted(Comparator.comparing(Person::getName))
                        .collect(Collectors.toList()).subList(0, 2);
            }


        }


        return new PageImpl<>(filtered);

    }


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
