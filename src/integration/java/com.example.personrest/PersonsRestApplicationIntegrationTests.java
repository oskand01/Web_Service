package com.example.personsrest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonsRestApplicationIntegrationTests {
    @LocalServerPort
    int port;

    @Autowired
    WebTestClient webTestClient;

    PersonAPI personAPI;

    @BeforeEach
    public void setup() throws Exception {
        personAPI = new PersonAPI(webTestClient);
    }

    @Test
    void test_add_group_to_person() {
        // Given
        PersonAPI.PersonDTO person1 = personAPI.createPerson("Arne Anka", "Ankeborg", 100);

        // When
        person1 = personAPI.addGroup(person1.getId(), "Ankeborgare");

        // Then
        assertEquals("Ankeborgare", person1.getGroups().get(0));
    }


    @Test
    void test_remove_group_from_person() {
        // Given
        PersonAPI.PersonDTO person1 = personAPI.createPerson("Arne Anka", "Ankeborg", 100);
        person1 = personAPI.addGroup(person1.getId(), "Ankeborgare");

        // When
        person1 = personAPI.removeGroup(person1.getId(), person1.getGroups().get(0));

        // Then
        assertEquals(0, person1.getGroups().size());
    }

}
