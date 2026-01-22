package de.bexa.savings.control;

import de.bexa.errorMessages.UserErrorMessages;
import de.bexa.repository.SavingsRepository;
import de.bexa.repository.UserRepository;
import de.bexa.savings.boundary.dto.SavingsDocumentResponse;
import de.bexa.savings.boundary.dto.SavingsItemRequest;
import de.bexa.savings.boundary.dto.SavingsItemResponse;
import de.bexa.savings.entity.Savings;
import de.bexa.savings.entity.SavingsItem;
import de.bexa.savings.entity.SavingsItemTypes;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

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

    public SavingsItemResponse addSavingsItem(String userId, SavingsItemRequest savingsItemRequest) {
        Savings savings = savingsRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, UserErrorMessages.USER_SAVINGS_DOCUMENT_NOT_FOUND(userId)));

        validateSavingsItemRequest(savingsItemRequest);

        SavingsItem newItem = SavingsItem.builder()
                .id(getNextSavingsItemId(savings))
                .name(savingsItemRequest.getName())
                .type(setSavingsItemType(savingsItemRequest))
                .startAmount(savingsItemRequest.getStartAmount() != null ? savingsItemRequest.getStartAmount() : 0.0)
                .targetAmount(savingsItemRequest.getTargetAmount())
                .targetAmount(savingsItemRequest.getTargetAmount())
                .savingsRate(savingsItemRequest.getSavingsRate())
                .startDate(new Date().toString())
                .targetDate(savingsItemRequest.getTargetDate())
                .build();

        savings.getItems().add(newItem);
        savingsRepository.save(savings);

        return SavingsItemResponse.builder()
                .id(newItem.getId())
                .name(newItem.getName())
                .type(newItem.getType())
                .startAmount(newItem.getStartAmount())
                .targetAmount(newItem.getTargetAmount())
                .savingsRate(newItem.getSavingsRate())
                .startDate(newItem.getStartDate())
                .targetDate(newItem.getTargetDate())
                .build();
    }

    public void validateSavingsItemRequest(SavingsItemRequest request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Savings item name must not be null or blank.");
        }
    }

    public Integer getNextSavingsItemId(Savings savings) {
        return savings.getItems().isEmpty() ? 1 : savings.getItems().stream()
                .mapToInt(SavingsItem::getId)
                .max()
                .orElse(0) + 1;
    }

    public SavingsItemTypes setSavingsItemType(SavingsItemRequest request) {
        if (request.getTargetAmount() == null) {
            return SavingsItemTypes.AMOUNT;
        } else if (request.getTargetDate() == null) {
            return SavingsItemTypes.DATE;
        } else {
            return SavingsItemTypes.GOAL;
        }
    }

    public List<SavingsItemResponse> getAllSavingsItems(String userId) {
        return getSavingsDocument(userId).getSavingsItems();
    }
}