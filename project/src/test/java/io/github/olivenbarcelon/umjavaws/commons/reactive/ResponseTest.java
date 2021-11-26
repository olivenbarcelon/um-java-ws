package io.github.olivenbarcelon.umjavaws.commons.reactive;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import io.github.olivenbarcelon.umjavaws.user.UserAccountEntity;

public class ResponseTest {
    
    @Test
    public void response() {
        UserAccountEntity userAccountEntity1 = new UserAccountEntity();
        userAccountEntity1.setId(1);
        UserAccountEntity userAccountEntity2 = new UserAccountEntity();
        userAccountEntity2.setId(2);
        
        List<UserAccountEntity> entities = Arrays.asList(userAccountEntity1, userAccountEntity2);
        
        Pageable pageable = PageRequest.of(1, 20, Sort.by("id").descending());
        Page<UserAccountEntity> page = new PageImpl<>(entities, pageable, entities.size());
        
        Response response = new Response(page, "User Account has successfully created");
        Assertions.assertEquals("User Account has successfully created", response.getMessage());
    }
}
