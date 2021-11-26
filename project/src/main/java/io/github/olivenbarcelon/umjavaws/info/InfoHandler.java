package io.github.olivenbarcelon.umjavaws.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import io.github.olivenbarcelon.umjavaws.commons.reactive.Response;

@Component
public class InfoHandler {
    @Autowired
    private Info info;
    
    public Mono<ServerResponse> index(ServerRequest request) {
        Mono<Info> model = Mono.just(info);
        return model.flatMap(fm -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(new Response(fm))));
    }
}
