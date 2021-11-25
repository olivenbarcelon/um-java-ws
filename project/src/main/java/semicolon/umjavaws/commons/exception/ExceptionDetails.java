package semicolon.umjavaws.commons.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @since 2021-07-30 [JDK11]
 * @version 2021-11-25
 * @author Oliven C. Barcelon
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDetails {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
