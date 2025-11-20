package com.uj.expenseManager.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseStatusException extends RuntimeException {
    private final HttpStatus status;

    public ResponseStatusException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public ResponseStatusException(String message) {
        super(message);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

}

