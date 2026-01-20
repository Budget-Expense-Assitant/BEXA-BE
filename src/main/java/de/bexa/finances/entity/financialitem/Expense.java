package de.bexa.finances.entity.financialitem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Expense {
    private String id;
    private BigDecimal amount;
    private LocalDate date;
    private String source;
}
