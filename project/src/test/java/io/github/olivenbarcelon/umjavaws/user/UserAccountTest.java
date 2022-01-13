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
import io.github.olivenbarcelon.umjavaws.commons.exception.ExceptionDetails;
import io.github.olivenbarcelon.umjavaws.commons.utils.MapperUtility;
import io.github.olivenbarcelon.umjavaws.commons.utils.StringUtility;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Log4j2
public class UserAccountTest {
    @Autowired
    private WebTestClient webTestClient;
    
    @Test
    @Order(1)
    public void createUserAccountWithEmptyData() {
        log.info("Create user account with empty data");
        webTestClient.post().uri("/api/user-account")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(new UserAccountEntity()), UserAccountEntity.class)
            .exchange()
            .expectStatus().is4xxClientError().returnResult(ExceptionDetails.class)
            .getResponseBody().subscribe(s -> log.info("\n" + MapperUtility.toJson(s)));
    }
    
    @Test
    @Order(2)
    public void createUserAccountWithSuperAdmin() {
        log.info("Create user account with super admin");
        UserAccountEntity entity = new UserAccountEntity();
        entity.setUsername("superadmin");
        entity.setPassword("superadmin");
        entity.setRole(Role.SUPER_ADMIN.toString());
        var response = webTestClient.post().uri("/api/user-account")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(entity), UserAccountEntity.class)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody().jsonPath("$.data.uuid").isNotEmpty().returnResult();
        log.info(StringUtility.toString(response.getResponseBody()));
    }
    
    @Test
    @Order(3)
    public void createUserAccountWithUsernameExists() {
        log.info("Create user account with username exists");
        UserAccountEntity entity = new UserAccountEntity();
        entity.setUsername("superadmin");
        entity.setPassword("superadmin");
        webTestClient.post().uri("/api/user-account")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(entity), UserAccountEntity.class)
            .exchange()
            .expectStatus().is4xxClientError().returnResult(ExceptionDetails.class)
            .getResponseBody().subscribe(s -> log.info("\n" + MapperUtility.toJson(s)));
    }
    
    @Test
    @Order(4)
    public void createUserAccountWithSuperAdminExists() {
        log.info("Create user account with super admin exists");
        UserAccountEntity entity = new UserAccountEntity();
        entity.setUsername("admin");
        entity.setPassword("admin");
        entity.setRole(Role.SUPER_ADMIN.toString());
        webTestClient.post().uri("/api/user-account")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(entity), UserAccountEntity.class)
            .exchange()
            .expectStatus().is4xxClientError().returnResult(ExceptionDetails.class)
            .getResponseBody().subscribe(s -> log.info("\n" + MapperUtility.toJson(s)));
    }
    
    @Test
    @Order(5)
    public void createUserAccountWithDefaultRole() {
        log.info("Create user account with default role");
        UserAccountEntity entity = new UserAccountEntity();
        entity.setUsername("admin");
        entity.setPassword("admin");
        var response = webTestClient.post().uri("/api/user-account")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(entity), UserAccountEntity.class)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody().jsonPath("$.data.uuid").isNotEmpty().returnResult();
        log.info(StringUtility.toString(response.getResponseBody()));
    }
    
    @Test
    @Order(6)
    public void createUserAccountWithUserRole() {
        log.info("Create user account with user role");
        UserAccountEntity entity = new UserAccountEntity();
        entity.setUsername("user");
        entity.setPassword("user");
        entity.setRole(Role.USER.toString());
        var response = webTestClient.post().uri("/api/user-account")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(entity), UserAccountEntity.class)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody().jsonPath("$.data.uuid").isNotEmpty().returnResult();
        log.info(StringUtility.toString(response.getResponseBody()));
    }
}
