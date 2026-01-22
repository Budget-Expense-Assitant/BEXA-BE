package de.bexa.user.boundary;

import de.bexa.user.boundary.dto.UserRequest;
import de.bexa.user.control.UserService;
import de.bexa.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserControllerTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_success() {
        List<User> users = List.of(User.builder().username("a").build());
        when(userService.getAllUsers()).thenReturn(users);
        ResponseEntity<List<User>> resp = userController.getAllUsers();
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(users, resp.getBody());
    }

    @Test
    void getUserById_success() {
        User user = User.builder().id("1").username("a").build();
        when(userService.getUserById("1")).thenReturn(user);
        ResponseEntity<User> resp = userController.getUserById("1");
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(user, resp.getBody());
    }

    @Test
    void createUser_success() {
        UserRequest req = UserRequest.builder().username("a").password("pw123456").build();
        User user = User.builder().username("a").build();
        when(userService.createUser(anyString(), anyString())).thenReturn(user);
        ResponseEntity<?> resp = userController.createUser(req);
        assertEquals(HttpStatus.CREATED, resp.getStatusCode());
        assertEquals(user, resp.getBody());
    }

    @Test
    void updateUser_success() {
        UserRequest req = UserRequest.builder().username("a").password("pw123456").currentPassword("oldpw").build();
        User user = User.builder().username("a").build();
        when(userService.updateUser(anyString(), anyString(), anyString(), anyString())).thenReturn(user);
        ResponseEntity<?> resp = userController.updateUser("1", req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(user, resp.getBody());
    }
}
