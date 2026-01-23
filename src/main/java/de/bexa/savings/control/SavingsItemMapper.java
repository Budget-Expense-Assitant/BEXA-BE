package de.bexa.savings.control;

import de.bexa.savings.boundary.dto.SavingsItemResponse;
import de.bexa.savings.entity.SavingsItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SavingsItemMapper {
    SavingsItemResponse toResponse(SavingsItem savingsItem);

    List<SavingsItemResponse> toResponseList(List<SavingsItem> savingsItems);
}
