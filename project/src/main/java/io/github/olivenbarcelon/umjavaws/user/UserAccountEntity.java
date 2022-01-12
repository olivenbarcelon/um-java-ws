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
@Getter
@Setter
public class UserAccountEntity {
    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnoreProperties(allowGetters = false)
    private long id;
    private String uuid;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnoreProperties(allowGetters = false)
    private LocalDateTime deletedAt;
}
