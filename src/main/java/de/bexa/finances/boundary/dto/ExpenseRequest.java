package de.bexa.finances.boundary.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.bexa.errorMessages.ExpensesErrorMessages;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequest {

    private Long id;
    @NotNull(message = ExpensesErrorMessages.EXPENSE_AMOUNT_NOT_GIVEN)
    private BigDecimal amount;

    @NotNull(message = ExpensesErrorMessages.EXPENSE_DATE_NOT_GIVEN)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = ExpensesErrorMessages.EXPENSE_TARGET_NOT_GIVEN)
    private String target;
    @NotNull(message = ExpensesErrorMessages.EXPENSE_DESCRIPTION)
    private String description;

    @NotNull(message = ExpensesErrorMessages.EXPENSE_RECURRING_STATE)
    private Boolean recurring;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate expenseStartDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate expenseEndDate;

}
