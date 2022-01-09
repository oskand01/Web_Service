package com.example.personsrest.domain;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/persons/")
@AllArgsConstructor
public class PersonController {

    private PersonService personService;

    @GetMapping
    public List<PersonDTO> all() {
        return personService.all()
                .map(PersonController::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<PersonDTO> get(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(
                    toDTO(personService.get(id)));
        } catch (PersonNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PersonDTO> createAnimal(@RequestBody CreatePerson createPerson) {
        PersonDTO person = toDTO(
                personService.createPerson(
                        createPerson.getName(),
                        createPerson.getCity(), createPerson.getAge()));

        return ResponseEntity.ok().body(person);
    }

    @PutMapping("{id}")
    public ResponseEntity<PersonDTO> update(@PathVariable("id") String id, @RequestBody UpdatePerson updatePerson) {
        try {
            return ResponseEntity.ok(
                    toDTO(
                            personService.updatePerson(id,
                                    updatePerson.getName(), updatePerson.getCity(), updatePerson.getAge()
                            )));
        } catch (PersonNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<PersonDTO> delete(@PathVariable("id") String id) {
        try {
            personService.delete(id);
            return ResponseEntity.ok().build();
        } catch (PersonNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{personId}/addGroup/{groupName}")
    public ResponseEntity<PersonDTO> addGroup(@PathVariable("personId") String personId, @PathVariable("groupName") String groupName) {
        try {
            return ResponseEntity.ok(
                    toDTO(
                            personService.addGroup(personId,
                                    groupName)));
        } catch (PersonNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}/removeGroup/{groupId}")
    public ResponseEntity<PersonDTO> removeGroup(@PathVariable("id") String id, @PathVariable("groupId") String groupId) {
        try {
            return ResponseEntity.ok(
                    toDTO(
                            personService.removeGroup(id,
                                    groupId)));
        } catch (PersonNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

 /*   @GetMapping
    public List<PersonDTO> all(@RequestParam String search, @RequestParam int pagesize, @RequestParam int pagenumber) {
        return personService.allPaginated(search, pagesize, pagenumber).map(PersonController::toDTO)
                .collect(Collectors.toList());
    }
*/

    public static PersonDTO toDTO(Person person) {

        PersonDTO personDTO = new PersonDTO(person.getId(), person.getName(),
                person.getCity(), person.getAge(), person.getGroups());

        if(personDTO.getGroups().isEmpty()) {
            log.info("\n\npersonDTO.getGroups(): []\n");
        } else {
            log.info("\n\npersonDTO.getGroups().get(0): " + personDTO.getGroups().get(0) + "\n");
        }

        return personDTO;
    }
}