package io.github.olivenbarcelon.umjavaws.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class UserAccountRouter {
    
    @Bean
    public RouterFunction<ServerResponse> routeUserAccount(UserAccountHandler handler) {
        return RouterFunctions.route(RequestPredicates.POST("/api/user-account")
            .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::store);
    }
}
