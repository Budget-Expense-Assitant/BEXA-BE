package de.bexa.finances.boundary;

import de.bexa.finances.boundary.dto.ExpenseRequest;
import de.bexa.finances.boundary.dto.IncomeRequest;
import de.bexa.finances.controller.FinanceService;
import de.bexa.finances.entity.Finances;
import de.bexa.finances.entity.financialitem.Expense;
import de.bexa.finances.entity.financialitem.Income;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FinancesController implements FinancesApi {

    private final FinanceService financesService;

    public FinancesController(FinanceService service) {
        this.financesService = service;
    }

    public ResponseEntity<Finances> addIncome(
            @PathVariable String userId,
            @RequestBody @Valid IncomeRequest incomeDto) {

        Income income = new Income();
        income.setId(incomeDto.getId());
        income.setAmount(incomeDto.getAmount());
        income.setSource(incomeDto.getSource());
        income.setDate(incomeDto.getDate());
        income.setDescription(incomeDto.getDescription());
        income.setRecurring(incomeDto.getRecurring());

        if (Boolean.TRUE.equals(incomeDto.getRecurring())){
            income.setIncomeStartDate(incomeDto.getIncomeStartDate());
            income.setIncomeEndDate(incomeDto.getIncomeEndDate());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(financesService.addIncome(userId, income));
    }

    public  ResponseEntity<Finances> addExpense(
            @PathVariable String userId,
            @RequestBody @Valid ExpenseRequest expenseDto) {
        Expense expense = new Expense();
        expense.setId(expenseDto.getId());
        expense.setAmount(expenseDto.getAmount());
        expense.setTarget(expenseDto.getTarget());
        expense.setDate(expenseDto.getDate());
        expense.setDescription(expenseDto.getDescription());
        expense.setRecurring(expenseDto.getRecurring());
        if (Boolean.TRUE.equals(expenseDto.getRecurring())){
            expense.setExpenseStartDate(expenseDto.getExpenseStartDate());
            expense.setExpenseEndDate(expenseDto.getExpenseEndDate());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(financesService.addExpense(userId, expense));
    }

    public Finances getFinances(@PathVariable String userId) {
        return financesService.getFinances(userId);
    }


}
