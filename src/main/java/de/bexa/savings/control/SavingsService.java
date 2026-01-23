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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SavingsService {
    private final UserRepository userRepository;
    private final SavingsRepository savingsRepository;
    private final SavingsItemMapper savingsItemMapper;

    public SavingsDocumentResponse createSavingsDocument(String userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, UserErrorMessages.USER_ID_NOT_FOUND(userId));
        }
        else if (savingsRepository.findById(userId).stream().anyMatch(savings -> savings.getUserId().equals(userId))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, UserErrorMessages.USER_SAVINGS_DOCUMENT_ALREADY_EXISTS(userId));

        }

        Savings savings = Savings.builder()
                .userId(userId)
                .items(new ArrayList<>())
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

        if (savings.getItems() == null) {
            savings.setItems(new ArrayList<>());
        }

        SavingsItem newItem = SavingsItem.builder()
                .id(getNextSavingsItemId(savings))
                .name(savingsItemRequest.getName())
                .type(setSavingsItemType(savingsItemRequest))
                .startAmount(savingsItemRequest.getStartAmount() != null ? savingsItemRequest.getStartAmount() : 0.0)
                .targetAmount(savingsItemRequest.getTargetAmount())
                .savingsRate(savingsItemRequest.getSavingsRate())
                .startDate(new Date().toString())
                .targetDate(savingsItemRequest.getTargetDate())
                .build();

        savings.getItems().add(newItem);
        savingsRepository.save(savings);

        return savingsItemMapper.toResponse(newItem);
    }

    public void validateSavingsItemRequest(SavingsItemRequest request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Savings item name must not be null or blank.");
        }
    }

    public Integer getNextSavingsItemId(Savings savings) {
        List<SavingsItem> items = savings.getItems();
        if (items == null || items.isEmpty()) {
            return 1;
        }

        return items.stream()
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
        Savings savings = savingsRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, UserErrorMessages.USER_SAVINGS_DOCUMENT_NOT_FOUND(userId)));

        List<SavingsItem> items = savings.getItems();
        if (items == null || items.isEmpty()) {
            return List.of();
        }

        return savingsItemMapper.toResponseList(items);
    }

    public @Nullable SavingsItemResponse getSavingsItemById(String userId, String itemId) {
        Savings savings = savingsRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, UserErrorMessages.USER_SAVINGS_DOCUMENT_NOT_FOUND(userId)));

        SavingsItem item = savings.getItems().stream()
                .filter(i -> i.getId().toString().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Savings item with ID " + itemId + " not found."));

        return savingsItemMapper.toResponse(item);
    }
}