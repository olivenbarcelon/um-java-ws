package semicolon.umjavaws.commons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(value = { MapperException.class })
    public Mono<ServerResponse> internalServerError(RuntimeException exception, ServerRequest request) {
        ExceptionDetails details = new ExceptionDetails(LocalDateTime.now(ZoneId.systemDefault()), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), exception.getMessage(), request.path());
        
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(BodyInserters.fromValue(details));
    }
    
    @ExceptionHandler(value = { NotAcceptableException.class })
    public Mono<ServerResponse> notAcceptable(RuntimeException exception, ServerRequest request) {
        ExceptionDetails details = new ExceptionDetails(LocalDateTime.now(ZoneId.systemDefault()), HttpStatus.NOT_ACCEPTABLE.value(), HttpStatus.NOT_ACCEPTABLE.getReasonPhrase(), exception.getMessage(), request.path());
    
        return ServerResponse.status(HttpStatus.NOT_ACCEPTABLE)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(BodyInserters.fromValue(details));
    }
    
    @ExceptionHandler(value = { NotFoundException.class })
    public Mono<ServerResponse> notFound(RuntimeException exception, ServerRequest request) {
        ExceptionDetails details = new ExceptionDetails(LocalDateTime.now(ZoneId.systemDefault()), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), exception.getMessage(), request.path());
    
        return ServerResponse.status(HttpStatus.NOT_FOUND)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(BodyInserters.fromValue(details));
    }
}
