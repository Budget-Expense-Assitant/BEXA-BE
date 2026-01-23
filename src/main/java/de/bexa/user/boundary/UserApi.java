package de.bexa.user.boundary;

import de.bexa.user.boundary.dto.UserRequest;
import de.bexa.user.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/users")
public interface UserApi {
    @PostMapping("/register")
    ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest);

    @DeleteMapping("/{userId}")
    ResponseEntity<Void> deleteUserById(@PathVariable String userId);

    @GetMapping
    ResponseEntity<List<User>> getAllUsers();

    @GetMapping("/{userId}")
    ResponseEntity<User> getUserById(@PathVariable String userId);

    @PutMapping("/{userId}")
    ResponseEntity<?> updateUser(@PathVariable String userId, @Valid @RequestBody UserRequest userRequest);
}
