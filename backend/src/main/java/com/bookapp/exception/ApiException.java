package com.bookapp.exception;

import org.springframework.http.HttpStatus;

/** Exception that carries the HTTP status that should be returned to the client. */
public class ApiException extends RuntimeException {

    private final HttpStatus status;

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
