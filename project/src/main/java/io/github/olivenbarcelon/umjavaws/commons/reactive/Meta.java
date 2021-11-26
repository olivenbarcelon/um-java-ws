package io.github.olivenbarcelon.umjavaws.commons.reactive;

import org.springframework.data.domain.Page;

import lombok.Data;

/**
 * @since 2021-08-01 [JDK11]
 * @version 2021-11-25
 * @author Oliven C. Barcelon
 */
@Data
public class Meta {
    private Pagination pagination;
    
    public Meta(Page<?> page) {
        this.pagination = new Pagination(page);
    }
}
