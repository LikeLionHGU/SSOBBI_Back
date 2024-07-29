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
}
