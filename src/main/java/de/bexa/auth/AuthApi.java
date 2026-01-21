package de.bexa.auth;

import de.bexa.auth.dto.LoginRequest;
import de.bexa.auth.dto.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/auth")
public interface AuthApi {
    @PostMapping("/login")
    ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest);
}