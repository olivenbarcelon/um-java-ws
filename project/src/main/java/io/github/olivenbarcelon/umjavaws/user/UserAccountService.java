package io.github.olivenbarcelon.umjavaws.user;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.github.olivenbarcelon.umjavaws.commons.enums.Role;
import io.github.olivenbarcelon.umjavaws.commons.exception.NotAcceptableException;
import io.github.olivenbarcelon.umjavaws.commons.utils.StringUtility;
import io.github.olivenbarcelon.umjavaws.commons.utils.crypto.CryptoUtility;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Service
@Log4j2
public class UserAccountService {
    @Value("${secret.key}")
    private String secretKey;
    @Autowired
    private UserAccountRepository repository;
    
    public Mono<UserAccountEntity> add(UserAccountEntity model) {
        log.info("Create user account - Start");
        if(!StringUtility.isValid(model.getUsername(), model.getPassword())) throw new NotAcceptableException("Username and Password must not be blank");
        
        Mono<UserAccountEntity> response = repository.existsByUsername(model.getUsername())
            .map(m -> {
                if(m) throw new NotAcceptableException("Username has already taken");
                return model;
            })
            .then(repository.existsByRole(Role.SUPER_ADMIN.toString()))
            .map(m -> {
                if(m != true) {
                    log.info("Super Admin doesn't exists");
                    model.setRole(Role.SUPER_ADMIN.toString());
                }
                else {
                    log.info("Super Admin already exists");
                    model.setRole(model.getRole() != null ? model.getRole() : Role.USER.toString());
                    if(model.getRole().equalsIgnoreCase(Role.SUPER_ADMIN.toString())) throw new NotAcceptableException("Unable to create account with SUPER_ADMIN role");
                }
                return model;
            })
            .map(m -> {
                m.setUuid(CryptoUtility.generateUuid().toString());
                m.setPassword(CryptoUtility.encrypt(m.getPassword(), secretKey));
                m.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Manila")));
                return m;
            }).flatMap(repository::save);
        log.info("Create user account - Stop");
        return response;
    }
}
