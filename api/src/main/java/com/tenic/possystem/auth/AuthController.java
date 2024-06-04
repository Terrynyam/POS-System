package com.tenic.possystem.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Terrance Nyamfukudza
 * 30/5/2024
 */

@Data
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/authenticate")
@Tag(name = "Authentication", description = "authentication")
@CrossOrigin
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(description = "Login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }
}
