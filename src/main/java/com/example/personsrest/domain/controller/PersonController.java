package com.example.personsrest.domain.controller;

import com.example.personsrest.domain.entity.Person;
import com.example.personsrest.domain.exception.PersonNotFoundException;
import com.example.personsrest.domain.model.CreatePerson;
import com.example.personsrest.domain.model.PersonDTO;
import com.example.personsrest.domain.model.UpdatePerson;
import com.example.personsrest.domain.service.PersonService;
import com.example.personsrest.domain.exception.GroupNotFoundException;
import com.example.personsrest.remote.GroupRemote;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/persons")
@AllArgsConstructor
public class PersonController {

    private final PersonService personService;
    private final GroupRemote groupRemote;

    @GetMapping
    public List<PersonDTO> all(@RequestParam(required = false) Map<String, String> filter) {
        return filter.isEmpty() ?
                personService.all()
                        .map(this::toDTO)
                        .collect(Collectors.toList())
                : personService.filter(filter).stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<PersonDTO> get(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(toDTO(personService.get(id)));
        } catch (PersonNotFoundException e) {
            return notFound(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<PersonDTO> createPerson(@Valid @RequestBody CreatePerson createPerson) {
        PersonDTO person = toDTO(
                personService.createPerson(
                        createPerson.getName(),
                        createPerson.getCity(),
                        createPerson.getAge()));

        return ResponseEntity.ok().body(person);
    }

    @PutMapping("{id}")
    public ResponseEntity<PersonDTO> update(@PathVariable("id") String id, @Valid @RequestBody UpdatePerson updatePerson) {
        try {
            return ResponseEntity.ok(toDTO(
                    personService.updatePerson(id, updatePerson)));
        } catch (PersonNotFoundException e) {
            return notFound(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<PersonDTO> delete(@PathVariable("id") String id) {
        try {
            personService.delete(id);
            return ResponseEntity.ok().build();
        } catch (PersonNotFoundException e) {
            return notFound(e.getMessage());
        }
    }

    @PutMapping("{id}/addGroup/{groupName}")
    public ResponseEntity<PersonDTO> addGroup(@PathVariable("id") String id, @PathVariable("groupName") String groupName) {
        try {
            return ResponseEntity.ok(toDTO(personService.addGroup(id, groupName)));
        } catch (PersonNotFoundException e) {
            return notFound(e.getMessage());
        }
    }

    @DeleteMapping("{id}/removeGroup/{group}")
    public ResponseEntity<PersonDTO> removeGroup(@PathVariable("id") String id, @PathVariable("group") String group) {
        try {
            return ResponseEntity.ok(toDTO(personService.removeGroup(id, group)));
        } catch (PersonNotFoundException | GroupNotFoundException e) {
            return notFound(e.getMessage());
        }
    }

    public PersonDTO toDTO(Person person) {
        return new PersonDTO(
                person.getId(),
                person.getName(),
                person.getCity(),
                person.getAge(),
                getGroupNames(person)
        );
    }

    private List<String> getGroupNames(Person person) {
        return person.getGroups().stream()
                .map(groupRemote::getNameById)
                .collect(Collectors.toList());
    }

    private ResponseEntity<PersonDTO> notFound(String message) {
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand()
                .toUri();

        return ResponseEntity.notFound()
                .header("message", message)
                .header("path", location.getPath())
                .build();
    }
}
