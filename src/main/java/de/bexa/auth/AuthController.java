package de.bexa.auth;

import de.bexa.auth.dto.LoginRequest;
import de.bexa.auth.dto.LoginResponse;
import de.bexa.user.boundary.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthController implements AuthApi {
    private final AuthService authService;

    @Override
    public ResponseEntity<LoginResponse> loginUser(LoginRequest loginRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(LoginResponse.builder()
                .bearerToken(authService.loginUser(loginRequest.getUsername(), loginRequest.getPassword()))
                .build()
        );
    }
}
