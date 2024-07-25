package com.dreamteam.ssobbi.monthlyTargetAmount.controller.request;

import lombok.Data;

import java.util.ArrayList;

@Data
public class CategoryMonthlyTargetAmountRequests {
	ArrayList<CategoryMonthlyTargetAmountRequest> requests = new ArrayList<>();
}
