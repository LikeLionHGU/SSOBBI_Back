package com.dreamteam.ssobbi.record.controller.response;

import com.dreamteam.ssobbi.monthlyTargetAmount.dto.MonthlyTargetAmountDto;
import com.dreamteam.ssobbi.record.dto.ConsumptionDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class MonthlyConsumptionsAndTargetsByCategoryResponse2 {

  private Integer userIncome;
  private List<MonthlyConsumptionsAndTargetsByCategory> monthlyConsumptionsAndTargetsByCategories;

  public static MonthlyConsumptionsAndTargetsByCategoryResponse2 from(Integer userIncome, List<MonthlyTargetAmountDto> monthlyTargetAmountDtos, List<ConsumptionDto> consumptionDtos) {
    return MonthlyConsumptionsAndTargetsByCategoryResponse2.builder()
        .userIncome(userIncome)
        .monthlyConsumptionsAndTargetsByCategories(
                monthlyTargetAmountDtos.stream()
                    .map(
                        monthlyTargetAmountDto -> {
                          Integer consumption = ConsumptionDto.getAmountSumByCategory(monthlyTargetAmountDto.getCategory(), consumptionDtos);
                          return MonthlyConsumptionsAndTargetsByCategory.from(monthlyTargetAmountDto, consumption);
                        })
                    .toList())
        .build();
  }
  @Getter
  @Builder
  public static class MonthlyConsumptionsAndTargetsByCategory {
    private String category;
    private Integer consumption;
    private Integer target;

    public static MonthlyConsumptionsAndTargetsByCategory from(MonthlyTargetAmountDto monthlyTargetAmountDto, Integer consumption) {
      return MonthlyConsumptionsAndTargetsByCategory.builder()
          .category(monthlyTargetAmountDto.getCategory())
          .consumption(consumption)
          .target(monthlyTargetAmountDto.getAmount())
          .build();
    }
  }
}
