package de.bexa.savings.control;

import de.bexa.errorMessages.UserErrorMessages;
import de.bexa.repository.SavingsRepository;
import de.bexa.repository.UserRepository;
import de.bexa.savings.boundary.dto.SavingsDocumentResponse;
import de.bexa.savings.entity.Savings;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SavingsService {
    private final UserRepository userRepository;
    private final SavingsRepository savingsRepository;

    public SavingsDocumentResponse createSavingsDocument(String userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, UserErrorMessages.USER_ID_NOT_FOUND(userId));
        }
        else if (savingsRepository.findById(userId).stream().anyMatch(savings -> savings.getUserId().equals(userId))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, UserErrorMessages.USER_SAVINGS_DOCUMENT_ALREADY_EXISTS(userId));

        }

        Savings savings = Savings.builder()
                .userId(userId)
                .build();

        savingsRepository.save(savings);

        return SavingsDocumentResponse.builder()
                .id(savings.getId())
                .userId(savings.getUserId())
                .savingsItems(savings.getItems())
                .build();
    }

    public SavingsDocumentResponse getSavingsDocument(String userId) {
        Savings savings = savingsRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, UserErrorMessages.USER_SAVINGS_DOCUMENT_NOT_FOUND(userId)));

        return SavingsDocumentResponse.builder()
                .id(savings.getId())
                .userId(savings.getUserId())
                .savingsItems(savings.getItems())
                .build();
    }
}