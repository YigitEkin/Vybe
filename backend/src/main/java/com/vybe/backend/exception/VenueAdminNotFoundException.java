package com.vybe.backend.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class VenueAdminNotFoundException extends RuntimeException {
    public VenueAdminNotFoundException(String message) {
        super(message);
    }
}
