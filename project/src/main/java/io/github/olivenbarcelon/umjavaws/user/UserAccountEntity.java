package io.github.olivenbarcelon.umjavaws.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Source:
 *  [Reactive Relational Database Connectivity MySQL Implementation] - (https://github.com/mirromutth/r2dbc-mysql)
 *  [TLS version incompatibility when using latest JDKs with MySQL <8 #182] - https://github.com/mirromutth/r2dbc-mysql/issues/182
 */
@Table("user_account")
@Data
public class UserAccountEntity {
    @Id
    @JsonIgnore
    private long id;
    private String uuid;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @JsonIgnore
    private LocalDateTime deletedAt;
}
