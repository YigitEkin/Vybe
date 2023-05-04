package com.vybe.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class AlreadyCheckedInException extends RuntimeException{
    public AlreadyCheckedInException(String message) {
        super(message);
    }
}
