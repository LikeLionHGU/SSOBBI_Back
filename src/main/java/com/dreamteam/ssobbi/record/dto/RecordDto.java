package com.dreamteam.ssobbi.record.dto;

import com.dreamteam.ssobbi.record.controller.request.AddRecordRequest;
import com.dreamteam.ssobbi.record.controller.request.UpdateRecordRequest;
import com.dreamteam.ssobbi.record.entity.Record;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
public class RecordDto {
  private Long id;
  private Integer happinessRate;
  private String content;
  private LocalDate date;
  private List<ConsumptionDto> consumptions;

  public static RecordDto from(AddRecordRequest request) {
    return RecordDto.builder()
        .happinessRate(request.getHappinessRate())
        .content(request.getContent())
        .date(request.getDate())
        .consumptions(
            request.getConsumptions().stream()
                .map(
                    consumption ->
                        ConsumptionDto.builder()
                            .category(consumption.getCategory())
                            .amount(consumption.getAmount())
                            .targetAmount(consumption.getTargetAmount())
                            .isOverConsumption(consumption.getIsOverConsumption())
                            .build())
                .toList())
        .build();
  }

  public static RecordDto from(UpdateRecordRequest request) {
    return RecordDto.builder()
        .happinessRate(request.getHappinessRate())
        .content(request.getContent())
        .consumptions(
            request.getConsumptions().stream()
                .map(
                    consumption ->
                        ConsumptionDto.builder()
                            .category(consumption.getCategory())
                            .amount(consumption.getAmount())
                            .targetAmount(consumption.getTargetAmount())
                            .isOverConsumption(consumption.getIsOverConsumption())
                            .build())
                .toList())
        .build();
  }

  public static RecordDto withConsumptions(Record record) {
    return RecordDto.builder()
        .id(record.getId())
        .happinessRate(record.getHappinessRate())
        .content(record.getContent())
        .date(record.getDate())
        .consumptions(
            record.getConsumptions().stream()
                .map(
                    consumption ->
                        ConsumptionDto.builder()
                            .category(consumption.getCategory())
                            .amount(consumption.getAmount())
                            .targetAmount(consumption.getTargetAmount())
                            .isOverConsumption(consumption.getIsOverConsumption())
                            .build())
                .toList())
        .build();
  }

  public static RecordDto from(Record record) {
    return RecordDto.builder()
        .id(record.getId())
        .happinessRate(record.getHappinessRate())
        .content(record.getContent())
        .date(record.getDate())
        .consumptions(
            record.getConsumptions().stream()
                .map(
                    consumption ->
                        ConsumptionDto.builder()
                            .category(consumption.getCategory())
                            .amount(consumption.getAmount())
                            .targetAmount(consumption.getTargetAmount())
                            .isOverConsumption(consumption.getIsOverConsumption())
                            .build())
                .toList())
        .build();
  }

  public static Integer getHappinessAverage(List<RecordDto> recordDtos) {
    return recordDtos.stream().map(RecordDto::getHappinessRate).reduce(0, Integer::sum)
        / recordDtos.size();
  }

  public static Integer getHappinessStandardDeviation(List<RecordDto> recordDtos, Integer average) {
    return (int)
        Math.sqrt(
            recordDtos.stream()
                    .map(RecordDto::getHappinessRate)
                    .map(happinessRate -> Math.pow(happinessRate - average, 2))
                    .reduce(0.0, Double::sum)
                / recordDtos.size());
  }

  public Integer getOverConsumptionAmount() {
    return consumptions.stream()
        .filter(ConsumptionDto::getIsOverConsumption)
        .map(ConsumptionDto::getAmount)
        .reduce(0, Integer::sum);
  }

  public static Integer getMaxTotalOverConsumptionAmount(List<RecordDto> recordDtos) {
    return recordDtos.stream()
        .map(RecordDto::getOverConsumptionAmount)
        .max(Integer::compareTo)
        .orElse(0);
  }
}
