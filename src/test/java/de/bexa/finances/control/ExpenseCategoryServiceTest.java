package de.bexa.finances.control;

import de.bexa.finances.entity.Finances;
import de.bexa.finances.entity.financialitem.Expense;
import de.bexa.repository.FinanceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseCategoryServiceTest {
    @Test
    void testGetExpenseCategoryPercentages() {
        FinanceRepository repo = Mockito.mock(FinanceRepository.class);
        ExpenseCategoryService service = new ExpenseCategoryService(repo);

        Expense e1 = new Expense(1L, BigDecimal.valueOf(100), null, "Supermarkt", "Essen", false, null, null, "Lebensmittel");
        Expense e2 = new Expense(2L, BigDecimal.valueOf(50), null, "Tankstelle", "Benzin", false, null, null, "Mobilität");
        Expense e3 = new Expense(3L, BigDecimal.valueOf(50), null, "Supermarkt", "Snacks", false, null, null, "Lebensmittel");
        Finances finances = new Finances();
        finances.setExpenses(Arrays.asList(e1, e2, e3));
        Mockito.when(repo.findByUserId("user1")).thenReturn(Optional.of(finances));

        Map<String, Double> result = service.getExpenseCategoryPercentages("user1");
        assertEquals(2, result.size());
        assertEquals(75.0, result.get("Lebensmittel"), 0.01);
        assertEquals(25.0, result.get("Mobilität"), 0.01);
    }

    @Test
    void testGetExpenseCategoryPercentagesEmpty() {
        FinanceRepository repo = Mockito.mock(FinanceRepository.class);
        ExpenseCategoryService service = new ExpenseCategoryService(repo);
        Finances finances = new Finances();
        Mockito.when(repo.findByUserId("user2")).thenReturn(Optional.of(finances));
        Map<String, Double> result = service.getExpenseCategoryPercentages("user2");
        assertTrue(result.isEmpty());
    }
}
