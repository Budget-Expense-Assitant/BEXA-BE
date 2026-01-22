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
}