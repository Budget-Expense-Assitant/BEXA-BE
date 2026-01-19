package de.bexa.user.boundary;

import de.bexa.user.boundary.dto.UserCreationRequest;
import de.bexa.user.control.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserController implements UserApi {
    private final UserService userService;

    @Override
    public ResponseEntity<?> createUser(UserCreationRequest userCreationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userCreationRequest));
    }
}
