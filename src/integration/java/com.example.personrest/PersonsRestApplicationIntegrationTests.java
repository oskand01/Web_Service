package com.example.personrest;

import com.example.personsrest.domain.Person;
import com.example.personsrest.domain.PersonRepository;
import com.example.personsrest.remote.GroupRemote;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integrationtest")
public class PersonsRestApplicationIntegrationTests {
    @LocalServerPort
    int port;

    @Autowired
    WebTestClient webTestClient;

    PersonAPIIntegration personAPIIntegration;

    @BeforeEach
    public void setup() throws Exception {
        personAPIIntegration = new PersonAPIIntegration(webTestClient);
    }

    @Test
    void test_add_group_to_person() {
        // Given
        PersonAPIIntegration.PersonDTO person1 = personAPIIntegration.createPerson("Arne Anka", "Ankeborg", 100);

        // When
        person1 = personAPIIntegration.addGroup(person1, "Ankeborgare");

        // Then
        assertEquals("Ankeborgare", person1.getGroups().get(0));
    }


    // remove group

}
