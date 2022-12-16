package com.vybe.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( code = HttpStatus.CONFLICT)
public class PhoneNumberTakenException extends RuntimeException {
    public PhoneNumberTakenException(String message) {
        super(message);
    }
}
