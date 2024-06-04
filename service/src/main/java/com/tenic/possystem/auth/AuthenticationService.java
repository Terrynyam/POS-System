package com.tenic.possystem.auth;

/**
 * @author Terrance Nyamfukudza
 * 4/6/2024
 */
public interface AuthenticationService {

    LoginResponse login(LoginRequest request);
}
