package com.example.personsrest.domain.repository;

import com.example.personsrest.domain.entity.Person;
import com.example.personsrest.domain.entity.PersonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonDBRepository extends JpaRepository<PersonEntity, String> {
    Person save(Person person);
    Page<Person> findAllByNameContainingOrCityContaining(String name, String city, Pageable pageable);
}
