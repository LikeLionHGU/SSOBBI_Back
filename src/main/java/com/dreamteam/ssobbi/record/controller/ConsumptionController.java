package com.dreamteam.ssobbi.record.controller;

import com.dreamteam.ssobbi.monthlyTargetAmount.service.MonthlyTargetAmountService;
import com.dreamteam.ssobbi.record.controller.response.MonthlyConsumptionsAndTargetsByCategoryResponse;
import com.dreamteam.ssobbi.record.controller.response.MonthlyConsumptionsAndTargetsByCategoryResponse2;
import com.dreamteam.ssobbi.record.entity.Consumption;
import com.dreamteam.ssobbi.record.service.ConsumptionService;
import com.dreamteam.ssobbi.user.service.UserService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ConsumptionController {

    private final ConsumptionService consumptionService;
    private final UserService userService;
    private final MonthlyTargetAmountService monthlyTargetAmountService;


    @GetMapping("/{date}/category")
    public ResponseEntity<MonthlyConsumptionsAndTargetsByCategoryResponse> getMonthlyCategoryConsumption(
            @AuthenticationPrincipal Long userId, @PathVariable LocalDate date) {
        return ResponseEntity.ok().body(consumptionService.getMonthlyCategoryConsumption(userId, date));
    }

    @GetMapping("/{date}/category2")
    public ResponseEntity<MonthlyConsumptionsAndTargetsByCategoryResponse2> getMonthlyCategoryConsumption2(
            @AuthenticationPrincipal Long userId, @PathVariable LocalDate date) {
        return ResponseEntity.ok().body(MonthlyConsumptionsAndTargetsByCategoryResponse2.from(userService.getUser(userId).getIncome(), monthlyTargetAmountService.getMonthlyTargetAmount2(userId), consumptionService.getMonthlyConsumptionByUserAndDate(userId, date)));
    }
}
