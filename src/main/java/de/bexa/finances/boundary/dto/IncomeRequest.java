package de.bexa.finances.boundary.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.bexa.errorMessages.IncomeErrorMessages;
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
public class IncomeRequest {

    private Long id;
    @NotNull(message = IncomeErrorMessages.INCOME_AMOUNT_NOT_GIVEN)
    private BigDecimal amount;
    @NotNull(message = IncomeErrorMessages.INCOME_DATE_NOT_GIVEN)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    @NotNull(message = IncomeErrorMessages.INCOME_SOURCE_NOT_GIVEN)
    private String source;
    @NotNull(message = IncomeErrorMessages.INCOME_DESCRIPTION)
    private String description;
    @NotNull(message = IncomeErrorMessages.INCOME_RECURRING_STATE)
    private Boolean recurring;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate incomeStartDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate incomeEndDate;

}
