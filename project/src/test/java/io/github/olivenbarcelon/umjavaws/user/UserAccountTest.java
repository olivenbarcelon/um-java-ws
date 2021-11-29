package io.github.olivenbarcelon.umjavaws.user;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserAccountTest {
    @Autowired
    private WebTestClient webTestClient;
    
    @Test
    @Order(1)
    public void post() {
        // validate
        webTestClient.post().uri("/api/user-account")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(new UserAccountEntity()), UserAccountEntity.class)
            .exchange()
            .expectStatus().is4xxClientError();
        // store super admin
        UserAccountEntity entity = new UserAccountEntity();
        entity.setUsername("username");
        entity.setPassword("password");
        webTestClient.post().uri("/api/user-account")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(entity), UserAccountEntity.class)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.data.uuid").isNotEmpty();
        // store user
        webTestClient.post().uri("/api/user-account")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(entity), UserAccountEntity.class)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.data.uuid").isNotEmpty();
    }
}
