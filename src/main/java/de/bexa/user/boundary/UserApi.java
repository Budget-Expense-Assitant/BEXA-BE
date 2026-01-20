package de.bexa.user.boundary;

import de.bexa.user.boundary.dto.UserRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/users")
public interface UserApi {
    @PostMapping("/register")
    ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest);

    @PostMapping("/login")
    ResponseEntity<String> loginUser(@Valid @RequestBody UserRequest userRequest);

    @DeleteMapping("/{userId}")
    ResponseEntity<Void> deleteUserById(@PathVariable String userId);
}
