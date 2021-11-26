package io.github.olivenbarcelon.umjavaws.commons.reactive;

import org.springframework.data.domain.Page;

import lombok.Getter;

/**
 * @since 2021-08-01 [JDK11]
 * @version 2021-11-27
 * @author Oliven C. Barcelon
 */
@Getter
public class Pagination {
    private int perPage;
    private long total;
    private int count;
    private int totalPages;
    private int currentPage;
    
    /**
     * Constructor method with page
     * @param page
     * @since 2021-08-01 [JDK11]
     * @version 2021-11-27
     * @author Oliven C. Barcelon
     */
    public Pagination(Page<?> page) {
        perPage = page.getSize();
        total = page.getTotalElements();
        count = page.getNumberOfElements();
        currentPage = page.getNumber() + 1;
        totalPages = page.getTotalPages();
    }
}
