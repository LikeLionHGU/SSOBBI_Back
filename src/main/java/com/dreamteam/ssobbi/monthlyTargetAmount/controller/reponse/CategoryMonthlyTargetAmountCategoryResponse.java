package com.dreamteam.ssobbi.monthlyTargetAmount.controller.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class CategoryMonthlyTargetAmountCategoryResponse {
	private ArrayList<String> categories = new ArrayList<>();
}
