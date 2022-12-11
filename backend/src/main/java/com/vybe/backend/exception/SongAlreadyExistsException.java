package com.vybe.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( code = HttpStatus.CONFLICT)
public class SongAlreadyExistsException extends RuntimeException {
    public SongAlreadyExistsException(String message) {
        super(message);
    }
}
