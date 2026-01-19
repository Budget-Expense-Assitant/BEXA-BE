package de.bexa.user.control;

import de.bexa.repository.UserRepository;
import de.bexa.user.boundary.dto.UserCreationRequest;
import de.bexa.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(UserCreationRequest userCreationRequest) {
        User user = User.builder()
                .userName(userCreationRequest.getUserName())
                .password(userCreationRequest.getPassword())
                .createdAt(new Date())
                .build();
        userRepository.save(user);

        return user;
    }
}
