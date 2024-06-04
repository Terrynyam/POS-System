package com.tenic.possystem.auth;

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
public class LoginResponse {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private String expires_in;
    private String refresh_expires_in;
    private String id_token;
    private String note_before_policy;
    private String session_state;
    private String scope;
}
