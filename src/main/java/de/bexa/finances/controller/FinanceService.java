package de.bexa.finances.controller;

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

import java.util.ArrayList;
import java.util.List;

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
