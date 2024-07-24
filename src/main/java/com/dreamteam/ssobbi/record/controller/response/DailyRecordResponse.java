package com.dreamteam.ssobbi.record.controller.response;

import com.dreamteam.ssobbi.record.dto.ConsumptionDto;
import com.dreamteam.ssobbi.record.dto.RecordDto;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class DailyRecordResponse {
  private Long id;
  private Integer happinessRate;
  private String content;
  private List<Consumption> consumptions;
  private Boolean isRecorded;

  public static DailyRecordResponse from(RecordDto dto) {
    if (dto == null) {
      return DailyRecordResponse.builder().isRecorded(false).build();
    }
    return DailyRecordResponse.builder()
        .id(dto.getId())
        .happinessRate(dto.getHappinessRate())
        .content(dto.getContent())
        .consumptions(Consumption.from(dto.getConsumptions()))
        .isRecorded(true)
        .build();
  }

  @Getter
  @Builder
  private static class Consumption {
    private String category;
    private Integer amount;
    private Integer targetAmount;
    private Boolean isOverConsumption;

    public static List<Consumption> from(List<ConsumptionDto> consumptions) {
      return consumptions.stream()
          .map(
              consumption ->
                  Consumption.builder()
                      .category(consumption.getCategory())
                      .amount(consumption.getAmount())
                      .targetAmount(consumption.getTargetAmount())
                      .isOverConsumption(consumption.getIsOverConsumption())
                      .build())
          .toList();
    }
  }
}
