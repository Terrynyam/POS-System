package com.tenic.possystem.exceptions;

import lombok.Data;

/**
 * @author Terrance Nyamfukudza
 * 31/5/2024
 */
@Data
public class Error {

    private final int status;
    private final String error;

    private Error(int status, String error) {
        this.status = status;
        this.error = error;
    }

    public static Error of(int status, String error) {
        return new Error(status, error);
    }

    @Override
    public String toString() {
        return "Error{" +
                "status=" + status +
                ", error='" + error + '\'' +
                '}';
    }
}
