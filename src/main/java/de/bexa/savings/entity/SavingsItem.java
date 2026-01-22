package de.bexa.savings.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SavingsItem {
    private Integer id;
    private String name;
    private SavingsItemTypes type;
    private Double startAmount;
    private Double targetAmount;
    private Double savingsRate;
    private String startDate;
    private String targetDate;
}
