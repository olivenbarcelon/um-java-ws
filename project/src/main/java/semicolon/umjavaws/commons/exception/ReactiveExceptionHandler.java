package semicolon.umjavaws.commons.exception;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

/**
 * @since 2021-07-30 [JDK11]
 * @version 2021-11-25
 * @author Oliven C. Barcelon
 */
public class ReactiveExceptionHandler {
    
    /**
     * Exception
     * @param request {@link ServerRequest}
     * @param e {@link Throwable}
     * @return {@code Mono<ServerResponse>}
     * @since 2021-07-30 [JDK11]
     * @version 2021-11-25
     * @author Oliven C. Barcelon
     */
    public static Mono<ServerResponse> exception(ServerRequest request, Throwable e) {
        ExceptionDetails details = new ExceptionDetails();
        if(e instanceof NotAcceptableException) details = notAcceptable(request, e);
        else if(e instanceof UnauthorizedException) details = unAuthorize(request, e);
        else if(e instanceof NotFoundException) details = notFound(request, e);
        
        return ServerResponse.status(details.getStatus()).contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(details));
    }
    
    private static ExceptionDetails unAuthorize(ServerRequest request, Throwable e) {
        return new ExceptionDetails(LocalDateTime.now(ZoneId.systemDefault()), HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), e.getMessage(), request.path());
    }
    
    private static ExceptionDetails notFound(ServerRequest request, Throwable e) {
        return new ExceptionDetails(LocalDateTime.now(ZoneId.systemDefault()), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage(), request.path());
    }
    
    private static ExceptionDetails notAcceptable(ServerRequest request, Throwable e) {
        return new ExceptionDetails(LocalDateTime.now(ZoneId.systemDefault()), HttpStatus.NOT_ACCEPTABLE.value(), HttpStatus.NOT_ACCEPTABLE.getReasonPhrase(), e.getMessage(), request.path());
    }
}
