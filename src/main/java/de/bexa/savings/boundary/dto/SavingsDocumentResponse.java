package de.bexa.savings.boundary.dto;

import de.bexa.savings.entity.SavingsItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavingsDocumentResponse {
    private String id;
    private String userId;
    private ArrayList<SavingsItem> savingsItems = new ArrayList<>();
}
