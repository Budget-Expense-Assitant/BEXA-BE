package de.bexa.auth;

import de.bexa.auth.dto.LoginRequest;
import de.bexa.auth.dto.LoginResponse;
import de.bexa.auth.AuthService;
import de.bexa.repository.UserRepository;
import java.util.Optional;
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
    @Mock
    private UserRepository userRepository;
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
        when(userRepository.findByUsername(eq("user"))).thenReturn(Optional.of(
            de.bexa.user.entity.User.builder()
                .id("id1")
                .username("user")
                .password("pw")
                .build()
        ));
        ResponseEntity<LoginResponse> result = authController.loginUser(req);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("jwt-token", result.getBody().getBearerToken());
        assertEquals("id1", result.getBody().getUserId());
    }

    @Test
    void logoutUser_setsCookieToDelete() {
        ResponseEntity<Void> result = authController.logoutUser();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        String setCookie = result.getHeaders().getFirst("Set-Cookie");
        assertNotNull(setCookie);
        assertTrue(setCookie.contains("jwt="));
        assertTrue(setCookie.contains("Max-Age=0"));
    }
}
