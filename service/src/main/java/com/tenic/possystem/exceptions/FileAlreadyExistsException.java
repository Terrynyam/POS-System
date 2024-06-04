package com.tenic.possystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Terrance Nyamfukudza
 * 31/5/2024
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileAlreadyExistsException extends RuntimeException {

    public FileAlreadyExistsException(String message) {
        super(message);
    }
}
