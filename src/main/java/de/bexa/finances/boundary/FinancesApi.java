package de.bexa.finances.boundary;

import de.bexa.finances.boundary.dto.ExpenseRequest;
import de.bexa.finances.boundary.dto.IncomeRequest;
import de.bexa.finances.entity.Finances;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/users/{userId}/finances")
public interface FinancesApi {

    @RequestMapping("/addIncome")
    ResponseEntity<Finances> addIncome(String userId, IncomeRequest income);

    @RequestMapping("/addExpense")
    ResponseEntity<Finances> addExpense(String userId, ExpenseRequest expense);

    @GetMapping
    Finances getFinances(String userId);

}
