package com.tenic.possystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Terrance Nyamfukudza
 * 31/5/2024
 */
@ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
public class FailedToProcessRequestException extends RuntimeException {

    public FailedToProcessRequestException(String message) {
        super(message);
    }
}
