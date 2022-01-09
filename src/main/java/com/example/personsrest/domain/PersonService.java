package com.example.personsrest.domain;

import com.example.personsrest.remote.GroupRemote;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Slf4j
@Service
@AllArgsConstructor
public class PersonService {

    PersonRepository personRepository;
    GroupRemote groupRemote;

    public Stream<Person> all() {
        return personRepository.findAll().stream();
    }

    public Person get(String id) throws PersonNotFoundException {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }

    public Person createPerson(String name, String city, int age) {
        Person person = new PersonImpl(name, city, age);
        return personRepository.save(person);
    }

    public Person updatePerson(String id, String name, String city, int age) throws PersonNotFoundException {
        Person person = get(id);

        person.setName(name);
        person.setCity(city);
        person.setAge(age);
        return personRepository.save(person);
    }

    public void delete(String id) throws PersonNotFoundException {
        personRepository.delete(id);
    }

    public Person addGroup(String id, String name) throws PersonNotFoundException {
        Person person = get(id);
        String groupId = groupRemote.createGroup(name);
        log.info("GroupId: " + groupId);
        person.addGroup(groupId);
        return personRepository.save(person);
    }

    public Person removeGroup(String id, String groupId) throws PersonNotFoundException {
        Person person = get(id);

        person.removeGroup(groupId);

        return personRepository.save(person);

    }

}
