package com.dreamteam.ssobbi.record.controller.response;

import com.dreamteam.ssobbi.record.dto.ConsumptionDto;
import com.dreamteam.ssobbi.record.dto.RecordDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class WeeklyRecordSummaryResponse {
  private Integer happinessRate;
  private Integer totalOverConsumptionCount;
  private Integer totalConsumptionCount;
  private Integer overConsumptionRate;
  private List<TopFourOverConsumptionCategory> topFourOverConsumptionCategories;
  private Boolean isRecorded;

  public static WeeklyRecordSummaryResponse from(List<RecordDto> dtos) {
    if (dtos == null || dtos.isEmpty()) {
      return WeeklyRecordSummaryResponse.builder().isRecorded(false).build();
    }
    WeeklyRecordSummaryResponse response =
        WeeklyRecordSummaryResponse.builder()
            .happinessRate(dtos.stream().mapToInt(RecordDto::getHappinessRate).sum() / dtos.size())
            .totalOverConsumptionCount(
                dtos.stream()
                    .mapToInt(
                        dto ->
                            dto.getConsumptions().stream()
                                .mapToInt(consumption -> consumption.getIsOverConsumption() ? 1 : 0)
                                .sum())
                    .sum())
            .totalConsumptionCount(
                dtos.stream().mapToInt(dto -> dto.getConsumptions().size()).sum())
            .topFourOverConsumptionCategories(
                dtos.stream()
                    .flatMap(dto -> dto.getConsumptions().stream())
                    .filter(ConsumptionDto::getIsOverConsumption)
                    .collect(
                        java.util.stream.Collectors.groupingBy(
                            ConsumptionDto::getCategory,
                            java.util.stream.Collectors.summingInt(consumption -> 1)))
                    .entrySet()
                    .stream()
                    .sorted(
                        java.util.Comparator.comparing(
                            java.util.Map.Entry::getValue, java.util.Comparator.reverseOrder()))
                    .limit(4)
                    .map(
                        entry ->
                            TopFourOverConsumptionCategory.from(entry.getKey(), entry.getValue()))
                    .toList())
            .isRecorded(true)
            .build();
    response.setOverConsumptionRate(
        response.getTotalOverConsumptionCount() * 100 / response.getTotalConsumptionCount());
    return response;
  }

  @Getter
  @Builder
  private static class TopFourOverConsumptionCategory {
    private String category;
    private Integer count;

    private static TopFourOverConsumptionCategory from(String category, Integer count) {
      return TopFourOverConsumptionCategory.builder().category(category).count(count).build();
    }
  }
}
