package com.tenic.possystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Terrance Nyamfukudza
 * 31/5/2024
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectUsernameOrPasswordException extends RuntimeException {

    public IncorrectUsernameOrPasswordException(String message) {
        super(message);
    }
}
