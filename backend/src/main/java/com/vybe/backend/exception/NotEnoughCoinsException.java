package com.vybe.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.PAYMENT_REQUIRED)
public class NotEnoughCoinsException extends RuntimeException {
    public NotEnoughCoinsException(String s) {
        super(s);
    }
}
