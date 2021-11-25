package semicolon.umjavaws.commons.exception;

/**
 * @since 2021-07-30 [JDK11]
 * @version 2021-11-25
 * @author Oliven C. Barcelon
 */
public class UnauthorizedException extends RuntimeException {
    
    public UnauthorizedException(String message) {
        super(message);
    }
}