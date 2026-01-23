package de.bexa.finances.entity;

import de.bexa.finances.entity.financialitem.Expense;
import de.bexa.finances.entity.financialitem.Income;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "financial_entries")
public class Finances {
    @Id
    private String id;
    private String userId;

    private long expenseCounter = 0;
    private long incomeCounter = 0;

    private List<Expense> expenses = new ArrayList<>();
    private List<Income> incomes = new ArrayList<>();

}
