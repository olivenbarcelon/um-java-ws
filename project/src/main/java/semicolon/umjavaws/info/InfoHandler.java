package semicolon.umjavaws.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import semicolon.umjavaws.commons.exception.ReactiveExceptionHandler;
import semicolon.umjavaws.commons.reactive.Response;

@Component
public class InfoHandler {
    @Autowired
    private Info info;
    
    public Mono<ServerResponse> index(ServerRequest request) {
        Mono<Info> model = Mono.just(info);
        return model.flatMap(fm -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(new Response(fm))))
            .onErrorResume(e -> ReactiveExceptionHandler.exception(request, e));
    }
}
