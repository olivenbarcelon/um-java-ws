package io.github.olivenbarcelon.umjavaws.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class UserAccountService {
    @Autowired
    private UserAccountRepository repository;
    
    public Mono<UserAccountEntity> add() {
        return Mono.just(new UserAccountEntity());
    }
}
