package br.com.jafethenrique.JavaChallenge.exceptions.Details;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

@Data
@SuperBuilder
public class ExceptionDetails {
    protected HttpStatus status;
    protected String message;

//    public ExceptionDetails(HttpStatus status, String message, Throwable exception) {
//        this.status = status;
//        this.message = message;
//    }
//
//    public ExceptionDetails(HttpStatus status) {
//        this.status = status;
//    }
//
//    public ExceptionDetails() {
//    }
}
