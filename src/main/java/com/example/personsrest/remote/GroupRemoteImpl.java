package com.example.personsrest.remote;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@AllArgsConstructor
public class GroupRemoteImpl implements GroupRemote {
    WebClient webClient;
    KeyCloakToken token;

    public GroupRemoteImpl() {
        this.webClient = WebClient.builder()
                .baseUrl("https://groups.edu.sensera.se/api/")
                .build();

        this.token = KeyCloakToken.acquire("https://iam.sensera.se/", "test", "group-api", "user", "djnJnPf7VCQvp3Fc")
                .block();
    }

    @Override
    public String getNameById(String groupId) {
        Mono<Group> group = webClient.get()
                .uri("groups/" + groupId)
                .header("Authorization", "Bearer " + token.accessToken)
                .retrieve()
                .bodyToMono(Group.class)
                .single();

        return Objects.requireNonNull(group.block()).getName();
    }

    @Override
    public String createGroup(String name) {

        CreateGroup body = new CreateGroup(name);

        Mono<Group> group = webClient.post()
                .uri("groups/")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.accessToken)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(body))
                .retrieve()
                .bodyToMono(Group.class)
                .single();

        return Objects.requireNonNull(group.block()).getId();
    }

    @Override
    public String removeGroup(String id) {

        webClient.delete()
                .uri("groups/" + id)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.accessToken)
                .retrieve()
                .bodyToMono(Void.class).block();

        return id;
    }

    @Value
    public static class Group {
        String id;
        String name;

        @JsonCreator
        public Group(@JsonProperty("id") String id,
                     @JsonProperty("name") String name) {
            this.id = id;
            this.name = name;
        }
    }

    @Value
    static class CreateGroup {
        String name;
    }

}
