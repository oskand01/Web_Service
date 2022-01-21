package com.example.personsrest;

import com.example.personsrest.domain.entity.Person;
import com.example.personsrest.domain.repository.PersonRepository;
import com.example.personsrest.remote.GroupRemote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class PersonsRestApplicationTests {
    @LocalServerPort
    int port;

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    PersonRepository personRepository;

    @MockBean
    GroupRemote groupRemote;

    PersonAPI personApi;

    @BeforeEach
    void setUp() {
        personApi = new PersonAPI(webTestClient);

    }

    @Test
    void test_get_persons_success() {
        // Given
        Person person1 = mock(Person.class);
        when(person1.getName()).thenReturn("Arne Anka");
        String groupId = UUID.randomUUID().toString();
        when(person1.getGroups()).thenReturn(List.of(groupId));
        when(personRepository.findAll()).thenReturn(List.of(person1));
        when(groupRemote.getNameById(eq(groupId))).thenReturn("Ankeborgare");

        // When
        List<PersonAPI.PersonDTO> persons = personApi.all()
                .collectList()
                .block();

        // Then
        assertEquals(1, persons.size());
        assertEquals("Arne Anka", persons.get(0).getName());
    }

    @Test
    void test_get_person_success() {
        // Given
        Person person1 = mock(Person.class);
        String personId = UUID.randomUUID().toString();
        when(personRepository.findById(eq(personId))).thenReturn(Optional.of(person1));
        when(person1.getId()).thenReturn(personId);

        // When
        PersonAPI.PersonDTO person = personApi.get(personId)
                .block();

        // Then
        assertEquals(personId, person.getId());
        verify(personRepository, times(1)).findById(personId);
    }

    @Test
    void test_create_person_success() {
        // Given
        Person person2 = mock(Person.class);
        String personId = UUID.randomUUID().toString();
        when(personRepository.save(any(Person.class))).thenReturn(person2);
        when(person2.getId()).thenReturn(personId);
        when(person2.getName()).thenReturn("Mia");
        when(person2.getCity()).thenReturn("Johannesburg");

        // When
        PersonAPI.PersonDTO person = personApi.createPerson("Mia",  "Johannesburg", 70);

        // Then
        verify(personRepository, times(1)).save(any(Person.class));
        assertEquals("Mia", person.getName());
        assertEquals("Johannesburg", person.getCity());
    }

    @Test
    void test_update_person_success() {
        // Given
        Person person1 = mock(Person.class);
        Person person2 = mock(Person.class);
        String personId = UUID.randomUUID().toString();
        when(personRepository.findById(personId)).thenReturn(Optional.of(person1));
        when(personRepository.save(eq(person1))).thenReturn(person2);
        when(person2.getName()).thenReturn("Sofia");

        //When
        PersonAPI.PersonDTO personUpdated = personApi.updatePerson(personId, "Sofia", "Stockholm", 8);

        // Then
        assertEquals("Sofia", personUpdated.getName());
        verify(personRepository, times(1)).save(person1);
        verify(person1, times(1)).setName("Sofia");
        verify(personRepository, times(1)).findById(personId);
    }

    @Test
    void test_delete_person_success() {
        // Given
        Person person1 = mock(Person.class);
        String personId = UUID.randomUUID().toString();
        when(personRepository.findById(personId)).thenReturn(Optional.of(person1));

        // When
        personApi.deletePerson(personId)
                .block();

        // Then
        verify(personRepository, times(1)).delete(eq(personId));
    }

    @Test
    void test_add_group_to_person_success() {
        // Given
        String groupId = UUID.randomUUID().toString();
        String personId = UUID.randomUUID().toString();
        Person person = mock(Person.class);
        Person person2 = mock(Person.class);
        when(person2.getGroups()).thenReturn(List.of(groupId));
        when(personRepository.findById(eq(personId))).thenReturn(Optional.of(person));
        when(personRepository.save(eq(person))).thenReturn(person2);
        when(groupRemote.createGroup(eq("Ankeborgare"))).thenReturn(groupId);
        when(groupRemote.getNameById(eq(groupId))).thenReturn("Ankeborgare");

        // When
        PersonAPI.PersonDTO personWithAddedGroup = personApi.addGroup(personId, "Ankeborgare");

        // Then
        assertEquals("Ankeborgare", personWithAddedGroup.getGroups().get(0));
        verify(groupRemote, times(1)).createGroup(eq("Ankeborgare"));
        verify(person, times(1)).addGroup(eq(groupId));
    }

    @Test
    void test_remove_group_from_person_success() {
        // Given
        String groupId = UUID.randomUUID().toString();
        String personId = UUID.randomUUID().toString();
        Person person = mock(Person.class);
        when(person.getGroups()).thenReturn(List.of(groupId));
        Person person2 = mock(Person.class);
        when(person2.getGroups()).thenReturn(List.of());
        when(personRepository.findById(eq(personId))).thenReturn(Optional.of(person));
        when(personRepository.save(eq(person))).thenReturn(person2);

        // When
        PersonAPI.PersonDTO personWithRemovedGroup = personApi.removeGroup(personId, groupId);

        // Then
        assertTrue(groupId, personWithRemovedGroup.getGroups().isEmpty());
        verify(groupRemote, times(0)).removeGroup(eq(groupId));
        verify(person, times(1)).removeGroup(eq(groupId));
        verify(personRepository, times(1)).save(eq(person));
    }

    @Test
    void test_get_persons_filter_by_name_success() {
        // Given
        Person person1 = mock(Person.class);
        Page<Person> page = new PageImpl<>(List.of(person1));
        when(personRepository.findAllByNameContainingOrCityContaining(eq("Arne"), eq("Arne"),
                any(PageRequest.class))).thenReturn(page);

        // When
        List<PersonAPI.PersonDTO> persons = personApi.all("Arne", 10, 0)
                .collectList()
                .block();

        // Then
        assertEquals(1, persons.size());
    }

}
