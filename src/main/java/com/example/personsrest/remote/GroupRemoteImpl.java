package com.example.personsrest.remote;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Slf4j
@AllArgsConstructor
public class GroupRemoteImpl implements GroupRemote {
    WebClient webClient;
    KeyCloakToken token;

    public GroupRemoteImpl() {
        this.webClient = WebClient.builder()
                .baseUrl("https://groups.edu.sensera.se/api/")
                .build();
    }

    // New token for every webclient request to avoid timeout

    @Override
    public String getNameById(String groupId) {
        token = KeyCloakToken.acquire("https://iam.sensera.se/", "test", "group-api", "user", "djnJnPf7VCQvp3Fc")
                .block();

        return Objects.requireNonNull(webClient.get()
                .uri("groups/" + groupId)
                .header("Authorization", "Bearer " + token.accessToken)
                .retrieve()
                .bodyToMono(Group.class)
                .onErrorMap(e -> new Exception("Remote connection failed", e))
                .single()
                .block()).getName();

    }

    @Override
    public String createGroup(String name) {
        token = KeyCloakToken.acquire("https://iam.sensera.se/", "test", "group-api", "user", "djnJnPf7VCQvp3Fc")
                .block();

        CreateGroup body = new CreateGroup(name);

        return Objects.requireNonNull(webClient.post()
                .uri("groups/")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.accessToken)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(body))
                .retrieve()
                .bodyToMono(Group.class)
                .onErrorMap(e -> new Exception("Remote connection failed", e))
                .block()).getId();
    }

    @Override
    public String removeGroup(String id) {
        token = KeyCloakToken.acquire("https://iam.sensera.se/", "test", "group-api", "user", "djnJnPf7VCQvp3Fc")
                .block();

        webClient.delete()
                .uri("groups/" + id)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.accessToken)
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorMap(e -> new Exception("Remote connection failed", e))
                .block();

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
