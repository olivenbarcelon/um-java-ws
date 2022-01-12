package io.github.olivenbarcelon.umjavaws.user;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
@Setter
public class UserAccountEntity {
    @Id
    //@JsonIgnoreProperties(allowGetters = false, allowSetters = true)
    private long id;
    @Getter
    private String uuid;
    @Getter
    private String username;
    @Getter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Getter
    private String role;
    @Getter
    private LocalDateTime createdAt;
    @Getter
    private LocalDateTime updatedAt;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime deletedAt;
}
