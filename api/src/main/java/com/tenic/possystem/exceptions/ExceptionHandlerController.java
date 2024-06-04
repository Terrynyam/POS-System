package com.tenic.possystem.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Terrance Nyamfukudza
 * 31/5/2024
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
public class ExceptionHandlerController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(IncorrectUsernameOrPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    Error incorrectUsernameOrPassword(IncorrectUsernameOrPasswordException e) {
        LOGGER.info("Incorrect Username or Password : {}", e.getMessage());
        return Error.of(400, e.getMessage());
    }
    @ExceptionHandler(FileAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    Error recordAlreadyExistError(FileAlreadyExistsException e) {
        LOGGER.info("File already exist error: {}", e.getMessage());
        return Error.of(400, e.getMessage());
    }

    @ExceptionHandler(FileDoesNotExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    Error fileDoesNotExist(FileDoesNotExistsException e) {
        LOGGER.info("File does not exist error: {}", e.getMessage());
        return Error.of(400, e.getMessage());
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    Error invalidRequestError(InvalidRequestException e) {
        LOGGER.info("File already exist error: {}", e.getMessage());
        return Error.of(400, e.getMessage());
    }
    @ExceptionHandler(FailedToProcessRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    Error failedToProcessRequest(FailedToProcessRequestException e) {
        return Error.of(400, e.getMessage());
    }
}
