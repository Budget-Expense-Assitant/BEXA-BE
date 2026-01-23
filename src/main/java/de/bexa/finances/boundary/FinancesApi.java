package de.bexa.finances.boundary;

import de.bexa.finances.boundary.dto.ExpenseRequest;
import de.bexa.finances.boundary.dto.IncomeRequest;
import de.bexa.finances.entity.Finances;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/users/{userId}/finances")
public interface FinancesApi {
    @PostMapping("/incomes")
    ResponseEntity<Finances> addIncome(@PathVariable String userId, @RequestBody IncomeRequest income);

    @PostMapping("/expenses")
    ResponseEntity<Finances> addExpense(@PathVariable String userId, @RequestBody ExpenseRequest expense);

    @GetMapping
    ResponseEntity<Finances> getFinances(@PathVariable String userId);

    @PutMapping("/incomes/{incomeId}")
    ResponseEntity<Finances> updateIncome(@PathVariable String userId, @PathVariable long incomeId, @RequestBody IncomeRequest income);

    @DeleteMapping("/incomes/{incomeId}")
    ResponseEntity<Finances> deleteIncome(@PathVariable String userId, @PathVariable long incomeId);

    @PutMapping("/expenses/{expenseId}")
    ResponseEntity<Finances> updateExpense(@PathVariable String userId, @PathVariable long expenseId, @RequestBody ExpenseRequest expense);

    @DeleteMapping("/expenses/{expenseId}")
    ResponseEntity<Finances> deleteExpense(@PathVariable String userId, @PathVariable long expenseId);
}
