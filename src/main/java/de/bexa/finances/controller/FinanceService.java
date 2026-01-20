package de.bexa.finances.controller;

import de.bexa.finances.entity.Finances;
import de.bexa.finances.entity.financialitem.Expense;
import de.bexa.finances.entity.financialitem.Income;
import de.bexa.repository.FinanceRepository;
import org.springframework.stereotype.Service;

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

        finances.getIncome().add(income);
        return repo.save(finances);
    }

    public Finances addExpense(String userId, Expense expense) {
        Finances finances = repo.findByUserId(userId)
                .orElseGet(() -> {
                    Finances f = new Finances();
                    f.setUserId(userId);
                    return f;
                });

        finances.getExpense().add(expense);
        return repo.save(finances);
    }

    public Finances getFinances(String userId) {
        return repo.findByUserId(userId).orElse(null);
    }
}
