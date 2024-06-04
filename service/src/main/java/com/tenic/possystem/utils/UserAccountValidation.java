package com.tenic.possystem.utils;

import com.tenic.possystem.exceptions.FailedToProcessRequestException;
import com.tenic.possystem.exceptions.InvalidRequestException;
import com.tenic.possystem.useraccount.UserAccountRequest;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

/**
 * @author Terrance Nyamfukudza
 * 4/6/2024
 */
@RequiredArgsConstructor
public class UserAccountValidation {

    public static void validateUser(UserAccountRequest request) {
        if (request.getFirstname() == null || request.getFirstname().isEmpty()) {
            throw new FailedToProcessRequestException("First name cannot be empty");
        }
        if (request.getLastName() == null || request.getLastName().isEmpty()) {
            throw new FailedToProcessRequestException("Last name cannot be empty");
        }
        if (!isValidEmail(request.getEmail())) {
            throw new FailedToProcessRequestException("Invalid Email");
        }
        if (request.getDateOfBirth().isAfter(LocalDate.now()))
            throw new InvalidRequestException("Date of Birth should not be a future date!");

    }
    public static boolean isValidEmail(String email) {
        return email.matches("^(.+)@(.+)$");
    }
}
