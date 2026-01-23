package de.bexa.auth;

import de.bexa.auth.dto.LoginRequest;
import de.bexa.auth.dto.LoginResponse;
import de.bexa.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthController implements AuthApi {
    private final AuthService authService;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        String jwt = authService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());

        ResponseCookie jwtCookie = ResponseCookie.from("jwt", jwt)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("Lax")
                .maxAge(Duration.ofSeconds(authService.getTokenTtlSeconds()))
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(LoginResponse.builder()
                        .bearerToken(jwt)
                        .userId(userRepository.findByUsername(loginRequest.getUsername()).get().getId())
                        .build());
    }
}
