package de.bexa.finances.boundary;

import de.bexa.finances.boundary.dto.ExpenseRequest;
import de.bexa.finances.boundary.dto.IncomeRequest;
import de.bexa.finances.entity.Finances;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public interface FinancesApi {

    Finances addIncome(String userId, IncomeRequest income);
    Finances addExpense(String userId, ExpenseRequest expense);
    Finances getFinances(String userId);

}
