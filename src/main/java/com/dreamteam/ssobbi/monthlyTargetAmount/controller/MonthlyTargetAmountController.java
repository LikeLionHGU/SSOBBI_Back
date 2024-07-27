package com.dreamteam.ssobbi.monthlyTargetAmount.controller;

//import com.dreamteam.ssobbi.monthlyTargetAmount.controller.request.CategoryMonthlyTargetAmountRequest;
import com.dreamteam.ssobbi.monthlyTargetAmount.controller.reponse.CategoryMonthlyTargetAmountCategoryResponse;
import com.dreamteam.ssobbi.monthlyTargetAmount.controller.reponse.CategoryMonthlyTargetAmountResponse;
import com.dreamteam.ssobbi.monthlyTargetAmount.controller.request.CategoryMonthlyTargetAmountRequest;
import com.dreamteam.ssobbi.monthlyTargetAmount.controller.request.CategoryMonthlyTargetAmountRequests;
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
	public ResponseEntity<Void> getMonthlyTargetAmount(@AuthenticationPrincipal Long userId, @RequestBody CategoryMonthlyTargetAmountRequests request) {
		monthlyTargetAmountService.saveMonthlyTargetAmount(userId, request.getRequests());
		return ResponseEntity.ok().build();
	}

	@GetMapping("/monthly/TargetAmount")
	public ResponseEntity<CategoryMonthlyTargetAmountResponse> getMonthlyTargetAmount(@AuthenticationPrincipal Long userId) {
		return ResponseEntity.ok(monthlyTargetAmountService.getMonthlyTargetAmount(userId));
	}

	@GetMapping("/monthly/TargetAmount/category-list")
	public ResponseEntity<CategoryMonthlyTargetAmountCategoryResponse> getMonthlyTargetAmountByCategory(@AuthenticationPrincipal Long userId) {
		return ResponseEntity.ok(monthlyTargetAmountService.getMonthlyTargetAmountByCategory(userId));
	}

	@PatchMapping("/monthly/TargetAmount")
	public ResponseEntity<Void> updateMonthlyTargetAmount(@AuthenticationPrincipal Long userId, @RequestBody CategoryMonthlyTargetAmountRequests request) {
		monthlyTargetAmountService.updateMonthlyTargetAmount(userId, request.getRequests());
		return ResponseEntity.ok().build();
	}

}
