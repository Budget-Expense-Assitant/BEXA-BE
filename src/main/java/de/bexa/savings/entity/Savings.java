package de.bexa.savings.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Document(collection = "savings")
public class Savings {
    @Id
    private String id;

    private String userId;
    private ArrayList<SavingsItem> items;
}
