package com.vybe.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class StreakNotFoundException extends RuntimeException {
    public StreakNotFoundException(String message) {
        super(message);
    }
}
