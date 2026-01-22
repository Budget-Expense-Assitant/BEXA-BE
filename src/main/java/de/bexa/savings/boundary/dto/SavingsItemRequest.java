package de.bexa.savings.boundary.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavingsItemRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private Double startAmount = 0.0;
    private Double targetAmount;
    private Double savingsRate;
    private String targetDate;
}