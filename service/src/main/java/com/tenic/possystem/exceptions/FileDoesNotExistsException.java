package com.tenic.possystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Terrance Nyamfukudza
 * 31/5/2024
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileDoesNotExistsException extends RuntimeException {

    public FileDoesNotExistsException(String message) {
        super(message);
    }
}
