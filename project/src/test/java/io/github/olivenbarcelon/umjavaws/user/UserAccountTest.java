package io.github.olivenbarcelon.umjavaws.user;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import io.github.olivenbarcelon.umjavaws.commons.enums.Role;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserAccountTest {
    @Autowired
    private WebTestClient webTestClient;
    
    @Test
    @Order(1)
    public void post() {
        UserAccountEntity entity = new UserAccountEntity();
        // Validate user account if null or empty
        webTestClient.post().uri("/api/user-account")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(entity), UserAccountEntity.class)
            .exchange()
            .expectStatus().is4xxClientError();
        // Add SUPER_ADMIN role
        UserAccountEntity entity1 = new UserAccountEntity();
        entity1.setUsername("username");
        entity1.setPassword("password");
        webTestClient.post().uri("/api/user-account")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(entity1), UserAccountEntity.class)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.data.uuid").isNotEmpty();
        /*// Add SUPER_ADMIN role
        entity.setUsername("username");
        entity.setPassword("password");
        entity.setRole(Role.SUPER_ADMIN.toString());
        webTestClient.post().uri("/api/user-account")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(entity), UserAccountEntity.class)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.data.uuid").isNotEmpty();
        // Validate SUPER_ADMIN role
        webTestClient.post().uri("/api/user-account")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(entity), UserAccountEntity.class)
            .exchange()
            .expectStatus().is4xxClientError();
        // Add default role
        entity.setRole(null);
        webTestClient.post().uri("/api/user-account")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(entity), UserAccountEntity.class)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.data.uuid").isNotEmpty();
        // Add USER role
        entity.setRole(Role.USER.toString());
        webTestClient.post().uri("/api/user-account")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(entity), UserAccountEntity.class)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.data.uuid").isNotEmpty();*/
    }
}
