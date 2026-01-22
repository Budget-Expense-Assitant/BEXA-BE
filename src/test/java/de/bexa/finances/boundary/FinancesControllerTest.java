package de.bexa.finances.boundary;

import de.bexa.finances.boundary.dto.ExpenseRequest;
import de.bexa.finances.boundary.dto.IncomeRequest;
import de.bexa.finances.control.FinanceService;
import de.bexa.finances.entity.Finances;
import de.bexa.finances.entity.financialitem.Expense;
import de.bexa.finances.entity.financialitem.Income;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FinancesControllerTest {
    @Mock
    private FinanceService financeService;
    @InjectMocks
    private FinancesController controller;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void getFinances_success() {
        Finances finances = new Finances();
        when(financeService.getFinances("u")).thenReturn(finances);
        ResponseEntity<Finances> resp = controller.getFinances("u");
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(finances, resp.getBody());
    }

    @Test
    void addIncome_success() {
        IncomeRequest req = new IncomeRequest();
        Income income = new Income();
        Finances finances = new Finances();
        when(financeService.addIncome(anyString(), any(Income.class))).thenReturn(finances);
        ResponseEntity<Finances> resp = controller.addIncome("u", req);
        assertEquals(HttpStatus.CREATED, resp.getStatusCode());
        assertEquals(finances, resp.getBody());
    }

    @Test
    void updateIncome_success() {
        IncomeRequest req = new IncomeRequest();
        Finances finances = new Finances();
        when(financeService.updateIncome(anyString(), anyLong(), any(Income.class))).thenReturn(finances);
        ResponseEntity<Finances> resp = controller.updateIncome("u", 1L, req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(finances, resp.getBody());
    }

    @Test
    void addExpense_success() {
        ExpenseRequest req = new ExpenseRequest();
        Expense expense = new Expense();
        Finances finances = new Finances();
        when(financeService.addExpense(anyString(), any(Expense.class))).thenReturn(finances);
        ResponseEntity<Finances> resp = controller.addExpense("u", req);
        assertEquals(HttpStatus.CREATED, resp.getStatusCode());
        assertEquals(finances, resp.getBody());
    }

    @Test
    void updateExpense_success() {
        ExpenseRequest req = new ExpenseRequest();
        Finances finances = new Finances();
        when(financeService.updateExpense(anyString(), anyLong(), any(Expense.class))).thenReturn(finances);
        ResponseEntity<Finances> resp = controller.updateExpense("u", 1L, req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(finances, resp.getBody());
    }
}
