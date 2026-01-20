package de.bexa.finances.entity;

import de.bexa.finances.entity.financialitem.Expense;
import de.bexa.finances.entity.financialitem.Income;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Document(collection = "financial_entries")
public class Finances {
    @Id
    private String id;
    private String userId;

    private List<Expense> expense = new ArrayList<>();
    private List<Income> income = new ArrayList<>();

}
