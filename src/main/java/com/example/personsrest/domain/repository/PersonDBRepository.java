package com.example.personsrest.domain.repository;

import com.example.personsrest.domain.entity.Person;
import com.example.personsrest.domain.entity.PersonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonDBRepository extends JpaRepository<PersonEntity, Integer> {
    Optional<Person> findById(String id);
    Person save(Person person);
    Page<Person> findAllByNameContainingOrCityContaining(String name, String city, Pageable pageable);
    void deleteById(String id);
}
