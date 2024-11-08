package com.dreamteam.ssobbi.record.dto;

import com.dreamteam.ssobbi.record.entity.Consumption;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ConsumptionDto {
  private Long id;
  private String category;
  private Integer amount;
  private Integer targetAmount;
  private Boolean isOverConsumption;

  public static List<ConsumptionDto> from(List<Consumption> consumptions) {
    return consumptions.stream()
        .map(
            consumption ->
                ConsumptionDto.builder()
                    .id(consumption.getId())
                    .category(consumption.getCategory())
                    .amount(consumption.getAmount())
                    .targetAmount(consumption.getTargetAmount())
                    .isOverConsumption(consumption.getIsOverConsumption())
                    .build())
        .toList();
  }

  public static Integer getAmountSumByCategory(String category, List<ConsumptionDto> consumptionDtos) {
    return consumptionDtos.stream()
        .filter(consumptionDto -> consumptionDto.getCategory().equals(category))
        .mapToInt(ConsumptionDto::getAmount)
        .sum();
  }
}
