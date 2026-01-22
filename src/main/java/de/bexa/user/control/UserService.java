package de.bexa.user.control;

import de.bexa.errorMessages.UserErrorMessages;
import de.bexa.repository.SavingsRepository;
import de.bexa.repository.UserRepository;
import de.bexa.savings.entity.Savings;
import de.bexa.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SavingsRepository savingsRepository;

    public User createUser(String username, String password) {
        if (userRepository.findAll().stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(username))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, UserErrorMessages.USER_ALREADY_EXISTS);
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .createdAt(new Date())
                .build();
        userRepository.save(user);

        Savings savingsDocument = Savings.builder()
                .userId(userRepository.findByUsername(username).get().getId())
                .items(new java.util.ArrayList<>())
                .build();
        savingsRepository.save(savingsDocument);

        return user;
    }

    public void deleteUserById(String id) {
        // Aktuell eingeloggten Usernamen holen
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        String currentUsername = authentication.getName();
        // User aus DB holen
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, UserErrorMessages.USER_NOT_FOUND(currentUsername)));
        // Prüfen, ob die ID übereinstimmt
        if (!currentUser.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sie dürfen nur Ihr eigenes Konto löschen.");
        }
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public User updateUser(String id, String username, String currentPassword, String newPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, UserErrorMessages.USER_NOT_FOUND(currentUsername)));
        if (!currentUser.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sie dürfen nur Ihr eigenes Konto aktualisieren.");
        }
        if (username == null || username.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, UserErrorMessages.USERNAME_CANNOT_BE_EMPTY);
        }
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, UserErrorMessages.PASSWORD_CANNOT_BE_EMPTY);
        }
        if (newPassword.length() < 8) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, UserErrorMessages.PASSWORD_TOO_SHORT);
        }
        if (currentPassword == null || currentPassword.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, UserErrorMessages.CURRENT_PASSWORD_CANNOT_BE_EMPTY);
        }
        if (!passwordEncoder.matches(currentPassword, currentUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, UserErrorMessages.CURRENT_PASSWORD_INCORRECT);
        }
        currentUser.setUsername(username);
        currentUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(currentUser);
        return currentUser;
    }
}