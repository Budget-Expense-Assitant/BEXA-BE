package de.bexa.finances.control;

import de.bexa.errorMessages.ExpensesErrorMessages;
import de.bexa.errorMessages.IncomeErrorMessages;
import de.bexa.errorMessages.UserErrorMessages;
import de.bexa.finances.entity.Finances;
import de.bexa.finances.entity.financialitem.Expense;
import de.bexa.finances.entity.financialitem.Income;
import de.bexa.repository.FinanceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FinanceService {

    private final FinanceRepository repo;

    public FinanceService(FinanceRepository repo) {
        this.repo = repo;
    }

    public Finances addIncome(String userId, Income income) {
        Finances finances = repo.findByUserId(userId)
                .orElseGet(() -> {
                    Finances f = new Finances();
                    f.setUserId(userId);
                    return f;
                });
        long nextId = finances.getIncomeCounter() + 1;
        finances.setIncomeCounter(nextId);
        income.setId(nextId);

        finances.getIncomes().add(income);

        return repo.save(finances);
    }

    public Finances updateIncome(String userId, long incomeId, Income updatedIncome) {
        Finances finances = repo.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, UserErrorMessages.USER_NOT_FOUND));
        Income income = finances.getIncomes().stream()
                .filter(i -> i.getId() == incomeId)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, IncomeErrorMessages.INCOME_NOT_FOUND));
        income.setAmount(updatedIncome.getAmount());
        income.setDescription(updatedIncome.getDescription());
        income.setDate(updatedIncome.getDate());
        income.setSource(updatedIncome.getSource());
        income.setRecurring(updatedIncome.getRecurring());
        income.setIncomeStartDate(updatedIncome.getIncomeStartDate());
        income.setIncomeEndDate(updatedIncome.getIncomeEndDate());
        return repo.save(finances);
    }

    public Finances deleteIncome(String userId, long incomeId) {
        Finances finances = repo.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, UserErrorMessages.USER_NOT_FOUND));
        boolean removed = finances.getIncomes().removeIf(i -> i.getId() == incomeId);
        if (!removed) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, IncomeErrorMessages.INCOME_NOT_FOUND);
        }
        return repo.save(finances);
    }

    public Finances addExpense(String userId, Expense expense) {

        Finances finances = repo.findByUserId(userId)
                .orElseGet(() -> {
                    Finances f = new Finances();
                    f.setUserId(userId);
                    return f;
                });

        long nextId = finances.getExpenseCounter() + 1;
        finances.setExpenseCounter(nextId);

        expense.setId(nextId);

        finances.getExpenses().add(expense);
        return repo.save(finances);
    }

    public Finances updateExpense(String userId, long expenseId, Expense updatedExpense) {
        Finances finances = repo.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, UserErrorMessages.USER_NOT_FOUND));
        Expense expense = finances.getExpenses().stream()
                .filter(e -> e.getId() == expenseId)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ExpensesErrorMessages.EXPENSE_NOT_FOUND));
        expense.setAmount(updatedExpense.getAmount());
        expense.setDescription(updatedExpense.getDescription());
        expense.setDate(updatedExpense.getDate());
        expense.setTarget(updatedExpense.getTarget());
        expense.setRecurring(updatedExpense.getRecurring());
        expense.setExpenseStartDate(updatedExpense.getExpenseStartDate());
        expense.setExpenseEndDate(updatedExpense.getExpenseEndDate());
        return repo.save(finances);
    }

    public Finances deleteExpense(String userId, long expenseId) {
        Finances finances = repo.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, UserErrorMessages.USER_NOT_FOUND));
        boolean removed = finances.getExpenses().removeIf(e -> e.getId() == expenseId);
        if (!removed) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ExpensesErrorMessages.EXPENSE_NOT_FOUND);
        }
        return repo.save(finances);
    }

    public Finances getFinances(String userId) {
        return repo.findByUserId(userId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                UserErrorMessages.USER_NOT_FOUND
                        )
                );
    }
}
