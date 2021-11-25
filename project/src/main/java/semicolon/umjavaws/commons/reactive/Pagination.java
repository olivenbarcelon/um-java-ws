package semicolon.umjavaws.commons.reactive;

import org.springframework.data.domain.Page;

import lombok.Data;

/**
 * @since 2021-08-01 [JDK11]
 * @version 2021-11-25
 * @author Oliven C. Barcelon
 */
@Data
public class Pagination {
    private int perPage;
    private long total;
    private int count;
    private int totalPages;
    private int currentPage;
    
    public Pagination(Page<?> page) {
        perPage = page.getSize();
        total = page.getTotalElements();
        count = page.getNumberOfElements();
        currentPage = page.getNumber() + 1;
        totalPages = page.getTotalPages();
    }
}
