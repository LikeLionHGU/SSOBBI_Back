package com.dreamteam.ssobbi.monthlyTargetAmount.controller;

import com.dreamteam.ssobbi.monthlyTargetAmount.controller.reponse.CategoryMonthlyTargetAmountResponse;
import com.dreamteam.ssobbi.monthlyTargetAmount.controller.request.CategoryMonthlyTargetAmountRequest;
import com.dreamteam.ssobbi.monthlyTargetAmount.service.MonthlyTargetAmountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ssobbi/category")
public class MonthlyTargetAmountController {

	private final MonthlyTargetAmountService monthlyTargetAmountService;

	@PostMapping("/monthly/TargetAmount")
	public ResponseEntity<Void> getMonthlyTargetAmount(@AuthenticationPrincipal Long id, @RequestBody ArrayList<CategoryMonthlyTargetAmountRequest> request) {
		monthlyTargetAmountService.saveMonthlyTargetAmount(id, request);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/monthly/TargetAmount")
	public ResponseEntity<CategoryMonthlyTargetAmountResponse> updateMonthlyTargetAmount(@AuthenticationPrincipal Long userId, @RequestBody ArrayList<CategoryMonthlyTargetAmountRequest> request) {

		CategoryMonthlyTargetAmountResponse response = monthlyTargetAmountService.updateMonthlyTargetAmount(userId, request);
		return ResponseEntity.ok().body(response);
//		return ResponseEntity.ok().build();
	}

}
