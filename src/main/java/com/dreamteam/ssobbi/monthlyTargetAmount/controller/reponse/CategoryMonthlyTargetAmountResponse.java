package com.dreamteam.ssobbi.monthlyTargetAmount.controller.reponse;

import com.dreamteam.ssobbi.monthlyTargetAmount.controller.request.CategoryMonthlyTargetAmountRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class CategoryMonthlyTargetAmountResponse {

	ArrayList<CategoryMonthlyTargetAmountRequest> responses = new ArrayList<>();
}
