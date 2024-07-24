package com.dreamteam.ssobbi.user.controller.response;

import lombok.Data;

@Data
public class UpdateUseIncome {
	private String name;
	private int income;

	public UpdateUseIncome(String name, int income) {
		this.name = name;
		this.income = income;
	}
}
