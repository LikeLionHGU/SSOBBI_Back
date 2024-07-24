package com.dreamteam.ssobbi.monthlyTargetAmount.controller.request;

import lombok.Getter;

@Getter
public class CategoryMonthlyTargetAmountRequest {

	private String category;
	private Integer amount;

	public CategoryMonthlyTargetAmountRequest(String category, Integer amount) {
		this.category = category;
		this.amount = amount;
	}
}
