package semicolon.umjavaws.commons.reactive;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import lombok.Data;
import semicolon.umjavaws.commons.utils.StringUtility;

/**
 * @since 2021-08-01 [JDK11]
 * @version 2021-11-25
 * @author Oliven C. Barcelon
 */
@Data
public class Response {
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Object data;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Meta meta;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Map<String, Object> sort;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private String message;
    
    public Response(String message) {
        this.message = message;
    }
    
    public Response(Object object) {
        if(object instanceof Page) {
            Page<?> data = (Page<?>) object;
            this.data = data.getContent();
            this.meta = new Meta(data);
            if(data.getSort().isSorted()) {
                Map<String, Object> sort = data.getSort().stream().collect(Collectors.toMap(Sort.Order::getProperty, Sort.Order::getDirection, (x, y) -> Sort.Direction.valueOf(x + ", " + y), LinkedHashMap::new));
                this.sort = StringUtility.toSnakeCase(sort);
            }
        }
        else {
            this.data = object;
        }
    }
    
    public Response(Object object, String message) {
        this(object);
        this.message = message;
    }
}
