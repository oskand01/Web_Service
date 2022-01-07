package com.example.personsrest;

import com.example.personsrest.domain.Person;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.channels.MembershipKey;
import java.util.List;

@AllArgsConstructor
public class PersonAPI {

    private static final String BASE_URL = "/api/persons/";

    WebTestClient webTestClient;

    public Flux<PersonDTO> all() {
        return webTestClient.get().uri(BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(PersonDTO.class)
                .getResponseBody();
    }

    public Flux<PersonDTO> all(String search, int pagesize, int pagenumber) {
        return webTestClient.get().uri("/api/persons?search="+search+"&pagenumber="+pagenumber+"&pagesize="+pagesize)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(PersonDTO.class)
                .getResponseBody();
    }

    public Mono<PersonDTO> get(String id) {
        return webTestClient.get().uri(BASE_URL + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(PersonDTO.class)
                .getResponseBody()
                .single();
    }

    public PersonDTO createPerson(String name, String city, int age) {
        return webTestClient.post().uri(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreatePerson(name, city, age))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(PersonDTO.class)
                .getResponseBody()
                .blockLast();
    }

    public PersonDTO updatePerson(String id, String name, String city, int age) {
        return webTestClient.put().uri(BASE_URL + id)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UpdatePerson(name, city, age))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(PersonDTO.class)
                .getResponseBody()
                .blockLast();
    }

    public Mono<Void> deletePerson(String id) {
        return webTestClient.delete().uri(BASE_URL + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Void.class)
                .getResponseBody()
                .then();
    }

    public PersonDTO addGroup(String personId, String groupName) {
        return webTestClient.put().uri(BASE_URL + personId + "/addGroup/" + groupName)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(PersonDTO.class)
                .getResponseBody()
                .blockLast();
    }

    public PersonDTO removeGroup(String personId, String groupId) {
        return webTestClient.delete().uri(BASE_URL + personId + "/removeGroup/" + groupId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(PersonDTO.class)
                .getResponseBody()
                .blockLast();
    }

    @Value
    static class CreatePerson {
        String name;
        String city;
        int age;
    }

    @Value
    static class UpdatePerson {
        String name;
        String city;
        int age;
    }

    @Value
    static class PersonDTO {
        String id;
        String name;
        String city;
        int age;
        List<String> groups;

        @JsonCreator
        public PersonDTO(
                @JsonProperty("id") String id,
                @JsonProperty("name") String name,
                @JsonProperty("city") String city,
                @JsonProperty("age") int age,
                @JsonProperty("groups") List<String> groups) {
            this.id = id;
            this.name = name;
            this.city = city;
            this.age = age;
            this.groups = groups;
        }

    }


}
