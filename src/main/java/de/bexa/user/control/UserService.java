package de.bexa.user.control;

import de.bexa.errorMessages.UserErrorMessages;
import de.bexa.repository.UserRepository;
import de.bexa.user.boundary.dto.UserRequest;
import de.bexa.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(UserRequest userRequest) {
        if (userRepository.findAll().stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(userRequest.getUsername()))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, UserErrorMessages.USER_ALREADY_EXISTS);
        }


        User user = User.builder()
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .createdAt(new Date())
                .build();
        userRepository.save(user);

        return user;
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }
}
