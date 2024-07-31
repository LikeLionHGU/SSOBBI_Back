package com.dreamteam.ssobbi.record.entity;

import com.dreamteam.ssobbi.base.entity.BaseTime;
import com.dreamteam.ssobbi.record.dto.ConsumptionDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
  @JsonBackReference // 순환참조 방지
  private Record record;

  @Column(name = "amount", nullable = false)
  private Integer amount;

  @Column(name = "is_over_consumption", nullable = false)
  private Boolean isOverConsumption;

  @Column(name = "target_amount", nullable = false)
  private Integer targetAmount;

  @Column(name = "category", nullable = false)
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
