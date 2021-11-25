package semicolon.umjavaws.commons.exception;

/**
 * @since 2021-07-30 [JDK11]
 * @version 2021-11-25
 * @author Oliven C. Barcelon
 */
public class NotFoundException extends RuntimeException {
    
    public NotFoundException(String message) {
        super(message);
    }
}
