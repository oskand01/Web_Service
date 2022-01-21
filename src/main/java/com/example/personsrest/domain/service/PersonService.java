package com.example.personsrest.domain.service;

import com.example.personsrest.domain.entity.Person;
import com.example.personsrest.domain.entity.PersonEntity;
import com.example.personsrest.domain.exception.PersonNotFoundException;
import com.example.personsrest.domain.model.UpdatePerson;
import com.example.personsrest.domain.repository.PersonRepository;
import com.example.personsrest.domain.exception.GroupNotFoundException;
import com.example.personsrest.remote.GroupRemote;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Map;
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
        Person person = new PersonEntity(name, city, age);

        return personRepository.save(person);
    }

    public Person updatePerson(String id, UpdatePerson updatePerson) throws PersonNotFoundException {
        Person person = get(id);

        person.setName(updatePerson.getName());
        person.setCity(updatePerson.getCity());
        person.setAge(updatePerson.getAge());

        return personRepository.save(person);
    }

    public void delete(String id) throws PersonNotFoundException {
        if (personRepository.findById(id).isPresent()) {
            personRepository.delete(id);
        } else {
            throw new PersonNotFoundException(id);
        }
    }

    public Person addGroup(String id, String name) throws PersonNotFoundException {
        Person person = get(id);
        String groupId = groupRemote.createGroup(name);
        log.info("\n\nGroupId: " + groupId + "\n");
        person.addGroup(groupId);
        return personRepository.save(person);
    }

    public Person removeGroup(String id, String groupId) throws PersonNotFoundException, GroupNotFoundException {
        Person person = get(id);

        if (!person.getGroups().contains(groupId)) {
            throw new GroupNotFoundException(groupId);
        }

        person.removeGroup(groupId);
        return personRepository.save(person);
    }

    public Page<Person> filter(Map<String, String> params) {
        int pageSize = Integer.parseInt(params.getOrDefault("pagesize", "25"));
        int pageNumber = Integer.parseInt(params.getOrDefault("pagenumber", "0"));
        String search = params.getOrDefault("search", "");

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("name").and(Sort.by("city")));

        return personRepository.findAllByNameContainingOrCityContaining(search, search, pageRequest);
    }
}
