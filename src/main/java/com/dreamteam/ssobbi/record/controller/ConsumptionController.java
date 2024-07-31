package com.dreamteam.ssobbi.record.controller;

import com.dreamteam.ssobbi.record.controller.response.MonthlyConsumptionsAndTargetsByCategoryResponse;
import com.dreamteam.ssobbi.record.entity.Consumption;
import com.dreamteam.ssobbi.record.service.ConsumptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping ("/api/ssobbi/consumptions")
public class ConsumptionController {

	private final ConsumptionService consumptionService;

	public ConsumptionController(ConsumptionService consumptionService) {
		this.consumptionService = consumptionService;
	}

	@GetMapping("/monthly/category")
//	public ResponseEntity<ArrayList<ArrayList<Consumption>>> getMonthlyCategoryConsumption(@AuthenticationPrincipal Long userId) {
	public ResponseEntity<MonthlyConsumptionsAndTargetsByCategoryResponse> getMonthlyCategoryConsumption(@AuthenticationPrincipal Long userId) {
		return ResponseEntity.ok().body(consumptionService.getMonthlyCategoryConsumption(userId));
	}


}
