package com.dreamteam.ssobbi.user.controller.response;

import lombok.Data;

@Data
public class AboutUserIncomeResponse {
	private String name;
	private int income;

	public AboutUserIncomeResponse(String name, int income) {
		this.name = name;
		this.income = income;
	}
}
