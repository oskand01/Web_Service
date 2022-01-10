package com.example.personsrest.domain.service;

import com.example.personsrest.domain.Person;
import com.example.personsrest.domain.PersonImpl;
import com.example.personsrest.domain.exception.PersonNotFoundException;
import com.example.personsrest.domain.repository.PersonRepository;
import com.example.personsrest.remote.GroupRemote;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        log.info("\n\nGroupId: " + groupId + "\n");
        person.addGroup(groupId);
        return personRepository.save(person);
    }

    public Person removeGroup(String id, String groupId) throws PersonNotFoundException {
        Person person = get(id);

        person.removeGroup(groupId);

        return personRepository.save(person);
    }

  /*  public Page<Person> filter(Map<String, String> params) {
        int pagesize = 10;
        int pagenumber = 0;
        String search = "";
        log.info(params.get("pagesize"));

        params.values().forEach(log::info);
        params.keySet().forEach(log::info);

        if (params.containsKey("pagesize")) pagesize = Integer.parseInt(params.get("pagesize"));
        log.info("Size: " + pagesize);
        if (params.containsKey("pagenumber")) pagenumber = Integer.parseInt(params.get("pagenumber"));
        log.info("Number: " + pagenumber);
        if (params.containsKey("search")) search = params.get("search");

        PageRequest pageRequest = PageRequest.of(pagenumber, pagesize);

        return personRepository.findAllByNameContainingOrCityContaining(search, search, pageRequest);
    }*/

    public Page<Person> find(Map<String, String> searchParams) {
        PageRequest pageRequest = (searchParams.containsKey("pagesize") && searchParams.containsKey("pagenumber"))
                ? PageRequest.of(
                Integer.parseInt(searchParams.get("pagenumber")),
                Integer.parseInt(searchParams.get("pagesize")))
                : PageRequest.of(1, 10);

        return personRepository.findAllByNameContainingOrCityContaining(searchParams.get("search"), searchParams.get("search"), pageRequest);
    }
}
