package de.bexa.auth;

import de.bexa.auth.dto.LoginRequest;
import de.bexa.auth.dto.LoginResponse;
import de.bexa.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AuthControllerTest {
    @Mock
    private AuthService authService;
    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void loginUser_success() {
        LoginRequest req = new LoginRequest();
        req.setUsername("user");
        req.setPassword("pw");
        when(authService.loginUser(eq("user"), eq("pw"))).thenReturn("jwt-token");
        when(authService.getTokenTtlSeconds()).thenReturn(3600L);
        ResponseEntity<LoginResponse> result = authController.loginUser(req);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("jwt-token", result.getBody().getBearerToken());
    }
}
