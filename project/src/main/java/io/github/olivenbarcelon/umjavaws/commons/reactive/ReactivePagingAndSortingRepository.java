package io.github.olivenbarcelon.umjavaws.commons.reactive;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @since 2021-08-01 [JDK11]
 * @version 2021-11-25
 * @author Oliven C. Barcelon
 */
@NoRepositoryBean
public interface ReactivePagingAndSortingRepository<T, ID> extends ReactiveSortingRepository<T, ID> {
    
    default Mono<Page<T>> findAll(Pageable pageable) {
        boolean sorted = pageable.getSort().isSorted();
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        Flux<T> entities = sorted ? findAllByDeletedAtIsNull(pageable.getSort()) : findAllByDeletedAtIsNull();
        Mono<Page<T>> page = entities.collectList().map(m -> {
            List<T> list = new ArrayList<>();
            int from = pageNumber * pageSize;
            int to = (pageNumber + 1) * pageSize;
            for(int i = from;i < to;i++) {
                try {
                    T t = m.get(i);
                    if(t != null) list.add(t);
                }
                catch(Exception ex) {
                
                }
            }
            return new PageImpl<>(list, pageable, m.size());
        });
        return page;
    }
    
    Flux<T> findAllByDeletedAtIsNull();
    Flux<T> findAllByDeletedAtIsNull(Sort sort);
}
