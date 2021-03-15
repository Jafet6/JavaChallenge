package br.com.jafethenrique.JavaChallenge.handlers;

import br.com.jafethenrique.JavaChallenge.exceptions.Details.ApiErrorDetails;
import br.com.jafethenrique.JavaChallenge.exceptions.Details.ExceptionDetails;
import br.com.jafethenrique.JavaChallenge.exceptions.Details.ValidationExceptionDetails;
import br.com.jafethenrique.JavaChallenge.exceptions.EmptyEmailException;
import br.com.jafethenrique.JavaChallenge.exceptions.InvalidPasswordException;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sun.istack.Nullable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            EntityNotFoundException exception) {
        return new ResponseEntity<>(
                ApiErrorDetails.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message(exception.getMessage())
                        .build(), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(EntityExistsException.class)
    protected ResponseEntity<Object> handleEntityExists(
            EntityExistsException exception) {
        return new ResponseEntity<>(
                ApiErrorDetails.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message(exception.getMessage())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NoSuchAlgorithmException.class)
    protected ResponseEntity<Object> handleNoSuchAlgorithm(
            NoSuchAlgorithmException exception) {
        return new ResponseEntity<>(
                ApiErrorDetails.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message(exception.getMessage())
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(InvalidPasswordException.class)
    protected ResponseEntity<Object> handlePasswordNotMatching(
            InvalidPasswordException exception) {
        return new ResponseEntity<>(
                ApiErrorDetails.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message(exception.getMessage())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(JsonGenerationException.class)
    protected ResponseEntity<Object> handleJsonGenerationException(
            JsonGenerationException exception) {
        return new ResponseEntity<>(
                ApiErrorDetails.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message(exception.getMessage())
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(IOException.class)
    protected ResponseEntity<Object> handleIOException(
            IOException exception) {
        return new ResponseEntity<>(
                ApiErrorDetails.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message(exception.getMessage())
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(JsonMappingException.class)
    protected ResponseEntity<Object> handleJsonMappingException(
            JsonMappingException exception) {
        return new ResponseEntity<>(
                ApiErrorDetails.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message(exception.getMessage())
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(EmptyEmailException.class)
    protected ResponseEntity<ApiErrorDetails> handleEmptyEmailException(
            EmptyEmailException exception) {
        return new ResponseEntity<>(
                ApiErrorDetails.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message(exception.getMessage())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message(exception.getMessage())
                        .fields(fields)
                        .fieldsMessage(fieldsMessage)
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .status(status)
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(exceptionDetails, headers, status);
    }

}
