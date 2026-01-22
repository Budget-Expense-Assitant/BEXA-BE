package de.bexa.savings.boundary;

import de.bexa.savings.boundary.dto.SavingsDocumentResponse;
import de.bexa.savings.boundary.dto.SavingsItemRequest;
import de.bexa.savings.boundary.dto.SavingsItemResponse;
import de.bexa.savings.control.SavingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SavingsController implements SavingsApi{
    private final SavingsService savingsService;
    @Override
    public ResponseEntity<SavingsDocumentResponse> getSavingsDocument(String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(savingsService.getSavingsDocument(userId));
    }

    @Override
    public ResponseEntity<SavingsItemResponse> addSavingsItem(String userId, SavingsItemRequest savingsItemRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(savingsService.addSavingsItem(userId, savingsItemRequest));
    }

    @Override
    public ResponseEntity<List<SavingsItemResponse>> getAllSavingsItems(String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(savingsService.getAllSavingsItems(userId));
    }

    @Override
    public ResponseEntity<SavingsItemResponse> getSavingsItemById(String userId, String itemId) {
        return null;
    }
}