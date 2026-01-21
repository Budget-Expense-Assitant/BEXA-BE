package de.bexa.user.control;

import de.bexa.repository.UserRepository;
import de.bexa.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    void createUser_shouldCreateUser_whenUsernameIsUnique() {
        String username = "testuser";
        String password = "password";
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
        User user = userService.createUser(username, password);
        assertEquals(username, user.getUsername());
        assertEquals("encodedPassword", user.getPassword());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_shouldThrowException_whenUsernameExists() {
        String username = "testuser";
        User existingUser = User.builder().username(username).build();
        when(userRepository.findAll()).thenReturn(Collections.singletonList(existingUser));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.createUser(username, "password");
        });
        assertEquals(409, exception.getStatusCode().value());
    }

    @Test
    void deleteUserById_shouldThrowException_whenUserDeletesAnotherUser() {
        String userIdToDelete = "userIdToDelete";
        String currentUsername = "currentUser";
        User currentUser = User.builder().id("differentUserId").username(currentUsername).build();

        when(userRepository.findByUsername(currentUsername)).thenReturn(Optional.of(currentUser));
        doNothing().when(userRepository).deleteById(anyString());

        // Mock Authentication und SecurityContext
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(currentUsername);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.deleteUserById(userIdToDelete);
        });
        assertEquals(403, exception.getStatusCode().value());
    }
}
