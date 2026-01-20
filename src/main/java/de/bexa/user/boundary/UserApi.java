package de.bexa.user.boundary;

import de.bexa.user.boundary.dto.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/users")
public interface UserApi {
    @PostMapping("/create")
    ResponseEntity<?> createUser(@RequestBody UserRequest userRequest);

    @PostMapping("/login")
    ResponseEntity<?> loginUser(@RequestBody UserRequest userRequest);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUserById(@PathVariable String id);
}
