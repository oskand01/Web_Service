package com.example.personsrest.domain.repository;

import com.example.personsrest.domain.entity.Person;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.*;


@AllArgsConstructor
public class PersonRepositoryImpl implements PersonRepository {

    PersonDBRepository personDBRepository;

    @Override
    public Optional<Person> findById(String id) {

        return personDBRepository.findById(id).map(a -> a);
    }

    @Override
    public List<Person> findAll() {

        return new ArrayList<>(personDBRepository.findAll());
    }

    @Override
    public Page<Person> findAllByNameContainingOrCityContaining(String name, String city, Pageable pageable) {
        return personDBRepository.findAllByNameContainingOrCityContaining(name, city, pageable);
    }

    @Override
    public void deleteAll() {
    }

    @Override
    public Person save(Person person) {
        return personDBRepository.save(person);
    }

    @Override
    @Transactional
    public void delete(String id) {
        personDBRepository.deleteById(id);
    }
}
