package de.bexa.user.control;

import de.bexa.auth.JwtService;
import de.bexa.errorMessages.UserErrorMessages;
import de.bexa.repository.UserRepository;
import de.bexa.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

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

        return user;
    }

    public String loginUser(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, UserErrorMessages.USER_NOT_FOUND(username)));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // Update last login time
        userRepository.save(User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .createdAt(user.getCreatedAt())
                .lastLoginAt(new Date())
                .build());

        //SecurityContextHolder.getContext().setAuthentication(authentication);

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof UserDetails userDetails)) {
            throw new IllegalStateException("Authenticated principal is not UserDetails");
        }

        return jwtService.generateToken(userDetails);
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }
}