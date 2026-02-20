package uz.qodirov.warehouse.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;


@RestControllerAdvice
public class GlobalHandlerException {

    private ResponseEntity<ExceptionResponse> responseResponseEntity(
            final Exception e,
            final WebRequest exchange, final HttpStatus status) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .message(e.getMessage())
                        .occurred(LocalDateTime.now())
                        .status(status.value()).build(), status
        );
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> exceptionResponseResponseEntity(AccessDeniedException e
            , WebRequest exchange) {
        return responseResponseEntity(e, exchange, FORBIDDEN);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ExistsNameException.class)
    public ResponseEntity<ExceptionResponse> exceptionResponseResponseEntity(ExistsNameException e
            , WebRequest exchange) {
        return responseResponseEntity(e,
                exchange,
                FORBIDDEN);
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<ExceptionResponse> unauthorized(
            HttpClientErrorException.Unauthorized e, WebRequest exchange) {
        return responseResponseEntity(e, exchange, UNAUTHORIZED);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> badRequest(
            HttpMessageNotReadableException e, WebRequest exchange) {
        return responseResponseEntity(e, exchange, BAD_REQUEST);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(ByIdException.class)
    public ResponseEntity<ExceptionResponse> byIdNot(
            ByIdException e, WebRequest exchange) {
        return responseResponseEntity(e, exchange, NOT_FOUND);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ExceptionResponse> client(
            ClientException e, WebRequest exchange) {
        return responseResponseEntity(e, exchange, NOT_FOUND);
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(PhoneException.class)
    public ResponseEntity<ExceptionResponse> phone(
            PhoneException e, WebRequest exchange) {
        return responseResponseEntity(e, exchange, CONFLICT);
    }
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(
            MethodArgumentNotValidException e, WebRequest exchange) {
        String errorMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .message(errorMessage)
                        .occurred(LocalDateTime.now())
                        .status(BAD_REQUEST.value())
                        .build(),
                BAD_REQUEST
        );
    }
}
