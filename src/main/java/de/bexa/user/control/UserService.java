package de.bexa.user.control;

import de.bexa.errorMessages.UserErrorMessages;
import de.bexa.repository.UserRepository;
import de.bexa.user.boundary.dto.UserRequest;
import de.bexa.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(UserRequest userRequest) {
        if (userRepository.findAll().stream().anyMatch(user -> user.getUserName().equalsIgnoreCase(userRequest.getUserName()))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, UserErrorMessages.USER_ALREADY_EXISTS);
        }

        User user = User.builder()
                .userName(userRequest.getUserName())
                .password(userRequest.getPassword())
                .createdAt(new Date())
                .build();
        userRepository.save(user);

        return user;
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }
}
