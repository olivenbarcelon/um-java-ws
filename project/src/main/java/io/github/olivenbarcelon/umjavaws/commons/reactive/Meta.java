package io.github.olivenbarcelon.umjavaws.commons.reactive;

import org.springframework.data.domain.Page;

import lombok.Getter;

/**
 * @since 2021-08-01 [JDK11]
 * @version 2021-11-25
 * @author Oliven C. Barcelon
 */
@Getter
public class Meta {
    private Pagination pagination;
    
    /**
     * Constructor method with page
     * @param page
     * @since 2021-08-01 [JDK11]
     * @version 2021-11-27
     * @author Oliven C. Barcelon
     */
    public Meta(Page<?> page) {
        this.pagination = new Pagination(page);
    }
}
