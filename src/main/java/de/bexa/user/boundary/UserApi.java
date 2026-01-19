package de.bexa.user.boundary;

import de.bexa.user.boundary.dto.UserCreationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/users")
public interface UserApi {
    @PostMapping("/create")
    ResponseEntity<?> createUser(@RequestBody UserCreationRequest userCreationRequest);
}
