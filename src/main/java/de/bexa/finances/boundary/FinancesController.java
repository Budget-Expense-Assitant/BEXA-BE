package de.bexa.finances.boundary;

import de.bexa.finances.boundary.dto.ExpenseRequest;
import de.bexa.finances.boundary.dto.IncomeRequest;
import de.bexa.finances.control.FinanceService;
import de.bexa.finances.entity.Finances;
import de.bexa.finances.entity.financialitem.Expense;
import de.bexa.finances.entity.financialitem.Income;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class FinancesController implements FinancesApi {
    private final FinanceService financesService;

    private Income mapIncomeRequestToEntity(IncomeRequest incomeDto) {
        Income income = new Income();
        income.setId(incomeDto.getId());
        income.setAmount(incomeDto.getAmount());
        income.setSource(incomeDto.getSource());
        income.setDate(incomeDto.getDate());
        income.setDescription(incomeDto.getDescription());
        income.setRecurring(incomeDto.getRecurring());
        if (Boolean.TRUE.equals(incomeDto.getRecurring())) {
            income.setIncomeStartDate(incomeDto.getIncomeStartDate());
            income.setIncomeEndDate(incomeDto.getIncomeEndDate());
        } else {
            income.setIncomeStartDate(null);
            income.setIncomeEndDate(null);
        }
        return income;
    }

    private Expense mapExpenseRequestToEntity(ExpenseRequest expenseDto) {
        Expense expense = new Expense();
        expense.setId(expenseDto.getId());
        expense.setAmount(expenseDto.getAmount());
        expense.setTarget(expenseDto.getTarget());
        expense.setDate(expenseDto.getDate());
        expense.setDescription(expenseDto.getDescription());
        expense.setRecurring(expenseDto.getRecurring());
        if (Boolean.TRUE.equals(expenseDto.getRecurring())) {
            expense.setExpenseStartDate(expenseDto.getExpenseStartDate());
            expense.setExpenseEndDate(expenseDto.getExpenseEndDate());
        } else {
            expense.setExpenseStartDate(null);
            expense.setExpenseEndDate(null);
        }
        return expense;
    }

    @Override
    public ResponseEntity<Finances> addIncome(@PathVariable String userId, @RequestBody @Valid IncomeRequest incomeDto) {
        Income income = mapIncomeRequestToEntity(incomeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(financesService.addIncome(userId, income));
    }

    @Override
    public ResponseEntity<Finances> addExpense(@PathVariable String userId, @RequestBody @Valid ExpenseRequest expenseDto) {
        Expense expense = mapExpenseRequestToEntity(expenseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(financesService.addExpense(userId, expense));
    }

    @Override
    public ResponseEntity<Finances> getFinances(@PathVariable String userId) {
        return ResponseEntity.ok(financesService.getFinances(userId));
    }

    @Override
    public ResponseEntity<Finances> updateIncome(@PathVariable String userId, @PathVariable long incomeId, @RequestBody @Valid IncomeRequest incomeDto) {
        Income updatedIncome = mapIncomeRequestToEntity(incomeDto);
        return ResponseEntity.ok(financesService.updateIncome(userId, incomeId, updatedIncome));
    }

    @Override
    public ResponseEntity<Finances> deleteIncome(@PathVariable String userId, @PathVariable long incomeId) {
        return ResponseEntity.ok(financesService.deleteIncome(userId, incomeId));
    }

    @Override
    public ResponseEntity<Finances> updateExpense(@PathVariable String userId, @PathVariable long expenseId, @RequestBody @Valid ExpenseRequest expenseDto) {
        Expense updatedExpense = mapExpenseRequestToEntity(expenseDto);
        return ResponseEntity.ok(financesService.updateExpense(userId, expenseId, updatedExpense));
    }

    @Override
    public ResponseEntity<Finances> deleteExpense(@PathVariable String userId, @PathVariable long expenseId) {
        return ResponseEntity.ok(financesService.deleteExpense(userId, expenseId));
    }
}
