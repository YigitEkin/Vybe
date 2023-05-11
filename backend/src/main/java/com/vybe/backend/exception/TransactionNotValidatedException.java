package com.vybe.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class TransactionNotValidatedException extends RuntimeException {
    public TransactionNotValidatedException(String message) {
        super(message);
    }
}
