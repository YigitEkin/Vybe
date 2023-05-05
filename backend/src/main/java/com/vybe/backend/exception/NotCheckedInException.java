package com.vybe.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class NotCheckedInException extends RuntimeException{
    public NotCheckedInException(String message) {
        super(message);
    }
}
