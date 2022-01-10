package com.example.personsrest.domain.repository;

import com.example.personsrest.domain.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository {
    Optional<Person> findById(String id);
    List<Person> findAll();
    Page<Person> findAllByNameContainingOrCityContaining(String name, String city, Pageable pageable);

    void deleteAll();

    Person save(Person person);

    void delete(String id);
}
