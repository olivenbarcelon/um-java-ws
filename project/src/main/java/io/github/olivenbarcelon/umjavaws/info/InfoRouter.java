package io.github.olivenbarcelon.umjavaws.info;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class InfoRouter {
    
    @Bean
    public RouterFunction<ServerResponse> routeInfo(InfoHandler handler) {
        return RouterFunctions.route(GET("/api/info").and(accept(MediaType.APPLICATION_JSON)), handler::index);
    }
}
