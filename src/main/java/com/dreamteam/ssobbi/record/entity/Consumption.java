package com.dreamteam.ssobbi.record.entity;

import com.dreamteam.ssobbi.base.entity.BaseTime;
import com.dreamteam.ssobbi.record.dto.ConsumptionDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Consumption extends BaseTime {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "consumption_id", nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "record_id")
  private Record record;

  private Integer amount;

  private Boolean isOverConsumption;

  private Integer targetAmount;

  private String category;

  public static List<Consumption> listFrom(List<ConsumptionDto> consumptions, Record record) {
    return consumptions.stream()
        .map(
            consumption ->
                Consumption.builder()
                    .record(record)
                    .amount(consumption.getAmount())
                    .isOverConsumption(consumption.getIsOverConsumption())
                    .targetAmount(consumption.getTargetAmount())
                    .category(consumption.getCategory())
                    .build())
        .toList();
  }
}
