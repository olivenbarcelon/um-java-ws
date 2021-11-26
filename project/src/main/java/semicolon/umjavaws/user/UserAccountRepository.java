package semicolon.umjavaws.user;

import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;
import semicolon.umjavaws.commons.reactive.ReactivePagingAndSortingRepository;

@Repository
public interface UserAccountRepository extends ReactivePagingAndSortingRepository<UserAccountEntity, Long> {
    Mono<Boolean> existsByUsername(String username);
    Mono<Boolean> existsByRole(String role);
    Mono<Boolean> existsByUuidIsNotAndUsername(String uuid, String username);
    Mono<UserAccountEntity> findByUuid(String uuid);
    Mono<UserAccountEntity> findByUsername(String username);
    Mono<UserAccountEntity> findByUsernameAndPassword(String username, String password);
}
