package de.bexa.finances.boundary;

import de.bexa.finances.boundary.dto.ExpenseRequest;
import de.bexa.finances.boundary.dto.IncomeRequest;
import de.bexa.finances.controller.FinanceService;
import de.bexa.finances.entity.Finances;
import de.bexa.finances.entity.financialitem.Expense;
import de.bexa.finances.entity.financialitem.Income;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/users/{userId}/finances")
public class FinancesController implements FinancesApi {

    private final FinanceService financesService;

    public FinancesController(FinanceService service) {
        this.financesService = service;
    }
    @PostMapping("/income")
    public Finances addIncome(@PathVariable String userId, @RequestBody IncomeRequest incomeDto) {
        Income income = new Income();
        income.setAmount(income.getAmount());
        income.setSource(income.getSource());
        return financesService.addIncome(userId, income);
    }
    @PostMapping("/expense")
    public Finances addExpense(@PathVariable String userId, @RequestBody ExpenseRequest expenseDto) {
        Expense expense = new Expense();
        expense.setAmount(expenseDto.getAmount());
        return financesService.addExpense(userId, expense);
    }

    @GetMapping
    public Finances getFinances(@PathVariable String userId) {
        return financesService.getFinances(userId);
    }



}
