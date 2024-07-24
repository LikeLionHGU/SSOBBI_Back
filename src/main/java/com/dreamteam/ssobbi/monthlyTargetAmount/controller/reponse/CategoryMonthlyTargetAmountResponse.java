package com.dreamteam.ssobbi.monthlyTargetAmount.controller.reponse;

import com.dreamteam.ssobbi.monthlyTargetAmount.dto.MonthlyTargetAmountDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class CategoryMonthlyTargetAmountResponse {

	private String name; // 이걸 앞에 따로, 뒤에는 CategoryMonthlyTargetAmountResponse가 보내지는 걸로..
	ArrayList<MonthlyTargetAmountDto> responses = new ArrayList<>();
}
