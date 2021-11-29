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
    
    // Test POST request
    @Test
    @Order(1)
    public void post() {
        // Validate user account if null or empty
        webTestClient.post().uri("/api/user-account")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(new UserAccountEntity()), UserAccountEntity.class)
            .exchange()
            .expectStatus().is4xxClientError();
        // Store super admin
        UserAccountEntity entity = new UserAccountEntity();
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
        // Validate super admin
        webTestClient.post().uri("/api/user-account")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(entity), UserAccountEntity.class)
            .exchange()
            .expectStatus().is4xxClientError();
    }
    
    @Test
    @Order(2)
    public void postWithRoleIsNull() {
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
    }
    
    @Test
    @Order(3)
    public void postWithRoleIsUser() {
        UserAccountEntity entity = new UserAccountEntity();
        entity.setUsername("username");
        entity.setPassword("password");
        entity.setRole(Role.USER.toString());
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
