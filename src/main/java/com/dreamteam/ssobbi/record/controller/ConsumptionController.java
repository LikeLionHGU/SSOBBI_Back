package com.dreamteam.ssobbi.record.controller;

import com.dreamteam.ssobbi.record.controller.response.MonthlyConsumptionsAndTargetsByCategoryResponse;
import com.dreamteam.ssobbi.record.entity.Consumption;
import com.dreamteam.ssobbi.record.service.ConsumptionService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping ("/api/ssobbi/consumptions")
public class ConsumptionController {

	private final ConsumptionService consumptionService;

	public ConsumptionController(ConsumptionService consumptionService) {
		this.consumptionService = consumptionService;
	}

	@GetMapping("/{date}/category")
	public ResponseEntity<MonthlyConsumptionsAndTargetsByCategoryResponse> getMonthlyCategoryConsumption(
		@AuthenticationPrincipal Long userId, @PathVariable LocalDate date) {
		return ResponseEntity.ok().body(consumptionService.getMonthlyCategoryConsumption(userId, date));
	}


}
