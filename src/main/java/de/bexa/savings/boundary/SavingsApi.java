package de.bexa.savings.boundary;

import de.bexa.savings.boundary.dto.SavingsDocumentResponse;
import de.bexa.savings.boundary.dto.SavingsItemRequest;
import de.bexa.savings.boundary.dto.SavingsItemResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/users/{userId}/savings")
public interface SavingsApi {
    @GetMapping
    ResponseEntity<SavingsDocumentResponse> getSavingsDocument(@PathVariable String userId);

    @PostMapping("/items")
    ResponseEntity<SavingsItemResponse> addSavingsItem(@PathVariable String userId, @RequestBody SavingsItemRequest savingsItemRequest);

    @GetMapping("/items")
    ResponseEntity<List<SavingsItemResponse>> getAllSavingsItems(@PathVariable String userId);

    @GetMapping("/items/{itemId}")
    ResponseEntity<SavingsItemResponse> getSavingsItemById(@PathVariable String userId, @PathVariable String itemId);
}