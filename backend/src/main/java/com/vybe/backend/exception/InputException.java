package com.vybe.backend.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( code = org.springframework.http.HttpStatus.BAD_REQUEST)
public class InputException extends RuntimeException {
    public InputException(String message) {
        super(message);
    }
}
