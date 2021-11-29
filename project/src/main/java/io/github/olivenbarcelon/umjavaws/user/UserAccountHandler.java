package io.github.olivenbarcelon.umjavaws.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.olivenbarcelon.umjavaws.commons.exception.ReactiveExceptionHandler;
import io.github.olivenbarcelon.umjavaws.commons.reactive.Response;
import reactor.core.publisher.Mono;

@Component
public class UserAccountHandler {
    @Autowired
    private UserAccountService service;
    
    public Mono<ServerResponse> store(ServerRequest request) {
        Mono<UserAccountEntity> model = request.bodyToMono(UserAccountEntity.class);
        return model.flatMap(service::add).flatMap(fm -> ServerResponse.created(UriComponentsBuilder
                    .fromPath("/api/user-account/" + fm.getUuid()).build().toUri())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new Response(fm, "User Account has successfully created"))))
            .onErrorResume(e -> ReactiveExceptionHandler.exception(request, e));
    }
}
