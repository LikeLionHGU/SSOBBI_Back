package com.dreamteam.ssobbi.monthlyTargetAmount.dto;

import com.dreamteam.ssobbi.monthlyTargetAmount.entity.MonthlyTargetAmount;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MonthlyTargetAmountDto {
	private Long id;
	private Long userId;
	private Integer amount;
	private String category;

	public static MonthlyTargetAmountDto from(MonthlyTargetAmount monthlyTargetAmount) {
		return MonthlyTargetAmountDto.builder()
				.id(monthlyTargetAmount.getId())
				.userId(monthlyTargetAmount.getUser().getId())
				.amount(monthlyTargetAmount.getAmount())
				.category(monthlyTargetAmount.getCategory())
				.build();
	}
}
