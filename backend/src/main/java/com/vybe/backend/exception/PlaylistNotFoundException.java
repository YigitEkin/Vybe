package com.vybe.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PlaylistNotFoundException extends RuntimeException {
    public PlaylistNotFoundException(String message) {
        super(message);
    }
}
