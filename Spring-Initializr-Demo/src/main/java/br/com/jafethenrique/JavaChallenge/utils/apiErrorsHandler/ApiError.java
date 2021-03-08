package br.com.jafethenrique.JavaChallenge.utils.apiErrorsHandler;

import org.springframework.http.HttpStatus;

public class ApiError {
    private HttpStatus status;
    private String message;

    public ApiError(HttpStatus status, String message, Throwable exception) {
        this.status = status;
        this.message = message;
    }

    public ApiError(HttpStatus status) {
        this.status = status;
    }

    public ApiError() {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
