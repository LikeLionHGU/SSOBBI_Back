package com.dreamteam.ssobbi.record.controller.response;

import com.dreamteam.ssobbi.record.controller.response.enums.HappinessLevel;
import com.dreamteam.ssobbi.record.dto.ConsumptionDto;
import com.dreamteam.ssobbi.record.dto.RecordDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
public class MonthlyHappinessRecordSummaryResponse {
  private List<Record> records;

  public static MonthlyHappinessRecordSummaryResponse from(List<RecordDto> recordDtos) {
    Integer maxAmount = RecordDto.getMaxTotalOverConsumptionAmount(recordDtos);
    Integer HappinessAverage = RecordDto.getHappinessAverage(recordDtos);
    Integer happinessStandardDeviation =
        RecordDto.getHappinessStandardDeviation(recordDtos, HappinessAverage);

    return MonthlyHappinessRecordSummaryResponse.builder()
        .records(
            recordDtos.stream()
                .map(
                    recordDto ->
                        Record.from(
                            HappinessAverage, happinessStandardDeviation, recordDto, maxAmount))
                .filter(record -> record.getNormalizationOverConsumptionAmount() > 0)
                .toList())
        .build();
  }

  @Builder
  @Getter
  private static class Record {
    private LocalDate date;
    private Integer happinessRate;
    private Integer normalizationOverConsumptionAmount;
    private String happinessLevel;
    private List<String> overConsumptionCategories;

    public static Record from(
        Integer HappinessAverage,
        Integer happinessStandardDeviation,
        RecordDto recordDto,
        Integer maxAmount) {
      return Record.builder()
          .date(recordDto.getDate())
          .happinessRate(recordDto.getHappinessRate())
          .normalizationOverConsumptionAmount(
              maxAmount != 0 ? recordDto.getOverConsumptionAmount() * 100 / maxAmount : 0)
          .overConsumptionCategories(
              recordDto.getConsumptions().stream()
                  .filter(ConsumptionDto::getIsOverConsumption)
                  .map(ConsumptionDto::getCategory)
                  .toList())
          .happinessLevel(
              HappinessLevel.from(
                      HappinessAverage, recordDto.getHappinessRate(), happinessStandardDeviation)
                  .getKorean())
          .build();
    }
  }
}
