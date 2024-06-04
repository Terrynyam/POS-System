package com.tenic.possystem.auth;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Terrance Nyamfukudza
 * 4/6/2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
