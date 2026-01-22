package de.bexa.finances.control;

import de.bexa.errorMessages.ExpensesErrorMessages;
import de.bexa.errorMessages.IncomeErrorMessages;
import de.bexa.errorMessages.UserErrorMessages;
import de.bexa.finances.entity.Finances;
import de.bexa.finances.entity.financialitem.Expense;
import de.bexa.finances.entity.financialitem.Income;
import de.bexa.repository.FinanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FinanceServiceTest {
    @Mock
    private FinanceRepository financeRepository;
    @InjectMocks
    private FinanceService financeService;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void addIncome_success() {
        Finances finances = new Finances();
        finances.setUserId("u");
        finances.setIncomeCounter(0);
        finances.setIncomes(new ArrayList<>());
        when(financeRepository.findByUserId("u")).thenReturn(Optional.of(finances));
        when(financeRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        Income income = new Income();
        Finances result = financeService.addIncome("u", income);
        assertEquals(1, result.getIncomes().size());
    }

    @Test
    void updateIncome_notFound() {
        when(financeRepository.findByUserId("u")).thenReturn(Optional.empty());
        Income income = new Income();
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> financeService.updateIncome("u", 1L, income));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void deleteIncome_notFound() {
        when(financeRepository.findByUserId("u")).thenReturn(Optional.of(new Finances()));
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> financeService.deleteIncome("u", 1L));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void addExpense_success() {
        Finances finances = new Finances();
        finances.setUserId("u");
        finances.setExpenseCounter(0);
        finances.setExpenses(new ArrayList<>());
        when(financeRepository.findByUserId("u")).thenReturn(Optional.of(finances));
        when(financeRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        Expense expense = new Expense();
        Finances result = financeService.addExpense("u", expense);
        assertEquals(1, result.getExpenses().size());
    }

    @Test
    void updateExpense_notFound() {
        when(financeRepository.findByUserId("u")).thenReturn(Optional.empty());
        Expense expense = new Expense();
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> financeService.updateExpense("u", 1L, expense));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void deleteExpense_notFound() {
        when(financeRepository.findByUserId("u")).thenReturn(Optional.of(new Finances()));
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> financeService.deleteExpense("u", 1L));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }
}
