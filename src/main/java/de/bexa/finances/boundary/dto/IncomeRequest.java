package de.bexa.finances.boundary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncomeRequest {
    private String id;
    private BigDecimal amount;
    private String source;
    private boolean recurring;

}
