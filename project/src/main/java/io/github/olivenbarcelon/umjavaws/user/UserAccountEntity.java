package io.github.olivenbarcelon.umjavaws.user;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Source:
 *  [Reactive Relational Database Connectivity MySQL Implementation] - (https://github.com/mirromutth/r2dbc-mysql)
 *  [TLS version incompatibility when using latest JDKs with MySQL <8 #182] - https://github.com/mirromutth/r2dbc-mysql/issues/182
 */
@Table("user_account")
@Getter
@Setter
public class UserAccountEntity {
    @Id
    private long id;
    private String uuid;
    private String username;
    private String password;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
