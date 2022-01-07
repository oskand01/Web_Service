package com.example.personsrest.domain;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Slf4j
@Service
@AllArgsConstructor
public class PersonService {

    PersonRepository personRepository;


    public Stream<Person> all() {
        return personRepository.findAll().stream();
    }

    public Person get(String id) throws PersonNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    public Person createPerson(String name, String city, int age) {
        PersonImpl person = new PersonImpl(name,city, age);
        log.info(name);
        log.info(person.getName());
        return personRepository.save(person);

    }

    public Person updatePerson(String id, String name, String city, int age) throws PersonNotFoundException {
        Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));

        person.setName(name);
        person.setCity(city);
        person.setAge(age);
        return personRepository.save(person);
    }

    public void delete(String id) throws PersonNotFoundException {
        //Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));

        personRepository.delete(id);

    }

    public Person addGroup(String id, String name) throws PersonNotFoundException {
        PersonImpl person = (PersonImpl) personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));

        person.addGroup(name);
        return personRepository.save(person);
    }
}
