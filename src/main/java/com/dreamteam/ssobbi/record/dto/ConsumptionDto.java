package com.dreamteam.ssobbi.record.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ConsumptionDto {
  private Long id;
  private String category;
  private Integer amount;
  private Integer targetAmount;
  private Boolean isOverConsumption;
}
