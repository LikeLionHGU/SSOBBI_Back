package com.dreamteam.ssobbi.monthlyTargetAmount.controller;

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
	public ResponseEntity<Void> getMonthlyTargetAmount(@AuthenticationPrincipal Long userId, @RequestBody ArrayList<CategoryMonthlyTargetAmountRequest> request) {
		monthlyTargetAmountService.saveMonthlyTargetAmount(userId, request);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/monthly/TargetAmount")
	public ResponseEntity<ArrayList<CategoryMonthlyTargetAmountRequest>> getMonthlyTargetAmount(@AuthenticationPrincipal Long userId) {
		return ResponseEntity.ok(monthlyTargetAmountService.getMonthlyTargetAmount(userId));
	}

	@PatchMapping("/monthly/TargetAmount")
	public ResponseEntity<Void> updateMonthlyTargetAmount(@AuthenticationPrincipal Long userId, @RequestBody ArrayList<CategoryMonthlyTargetAmountRequest> request) {
		monthlyTargetAmountService.updateMonthlyTargetAmount(userId, request);
		return ResponseEntity.ok().build();
	}

}
