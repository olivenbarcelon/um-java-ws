package semicolon.umjavaws.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import semicolon.umjavaws.commons.reactive.Response;

@Component
public class UserAccountHandler {
    @Autowired
    private UserAccountService service;
    
    public Mono<ServerResponse> store(ServerRequest request) {
        return service.add().flatMap(fm -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(new Response(fm))));
    }
}
