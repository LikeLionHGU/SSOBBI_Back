package com.dreamteam.ssobbi.record.controller.response;

import com.dreamteam.ssobbi.record.dto.ConsumptionDto;
import com.dreamteam.ssobbi.record.dto.RecordDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Builder
@Getter
public class DailyRecordSummaryResponse {
  private Integer happinessRate;
  private String content;
  private Integer totalOverConsumptionCount;
  private List<String> overConsumptionCategories;
  private Boolean isRecorded;

  public static DailyRecordSummaryResponse from(RecordDto dto) {
    if (dto == null) {
      return DailyRecordSummaryResponse.builder().isRecorded(false).build();
    }
    int totalOverConsumptionCount = Optional.of(
            dto.getConsumptions().stream()
                    .filter(ConsumptionDto::getIsOverConsumption)
                    .mapToInt(consumption -> 1)
                    .sum()
    ).orElse(0);

    return DailyRecordSummaryResponse.builder()
        .happinessRate(dto.getHappinessRate())
        .content(dto.getContent())
        .totalOverConsumptionCount(totalOverConsumptionCount)
        .overConsumptionCategories(
            dto.getConsumptions().stream()
                .filter(ConsumptionDto::getIsOverConsumption)
                .map(ConsumptionDto::getCategory)
                .toList())
        .isRecorded(true)
        .build();
  }
}
