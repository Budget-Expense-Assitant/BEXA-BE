package de.bexa.finances.control;

import de.bexa.finances.entity.Finances;
import de.bexa.finances.entity.financialitem.Expense;
import de.bexa.repository.FinanceRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpenseCategoryService {
    private final FinanceRepository repo;

    public ExpenseCategoryService(FinanceRepository repo) {
        this.repo = repo;
    }

    /**
     * Berechnet die prozentuale Verteilung der Ausgaben nach Kategorie für einen Nutzer.
     * @param userId Die User-ID
     * @return Map mit Kategorie als Schlüssel und Prozentanteil als Wert
     */
    public Map<String, Double> getExpenseCategoryPercentages(String userId) {
        Finances finances = repo.findByUserId(userId).orElse(null);
        if (finances == null || finances.getExpenses().isEmpty()) {
            return new HashMap<>();
        }
        List<Expense> expenses = finances.getExpenses();
        double total = expenses.stream().mapToDouble(e -> e.getAmount().doubleValue()).sum();
        Map<String, Double> categorySums = new HashMap<>();
        for (Expense expense : expenses) {
            categorySums.merge(expense.getCategory(), expense.getAmount().doubleValue(), Double::sum);
        }
        Map<String, Double> percentages = new HashMap<>();
        for (Map.Entry<String, Double> entry : categorySums.entrySet()) {
            percentages.put(entry.getKey(), (entry.getValue() / total) * 100.0);
        }
        return percentages;
    }
}
