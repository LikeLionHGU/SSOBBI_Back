package com.dreamteam.ssobbi.monthlyTargetAmount.controller;

import com.dreamteam.ssobbi.monthlyTargetAmount.controller.reponse.CategoryMonthlyTargetAmountResponse;
import com.dreamteam.ssobbi.monthlyTargetAmount.controller.request.CategoryMonthlyTargetAmountRequest;
import com.dreamteam.ssobbi.monthlyTargetAmount.service.MonthlyTargetAmountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ssobbi/category")
public class MonthlyTargetAmountController {

	private final MonthlyTargetAmountService monthlyTargetAmountService;

	@PostMapping("/monthly/TargetAmount")
	public ResponseEntity<CategoryMonthlyTargetAmountResponse> getMonthlyTargetAmount(@AuthenticationPrincipal Long id, @RequestBody ArrayList<CategoryMonthlyTargetAmountRequest> request) {
		CategoryMonthlyTargetAmountResponse response = monthlyTargetAmountService.saveMonthlyTargetAmount(id, request);
		return ResponseEntity.ok().body(response);
	}

}
