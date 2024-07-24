package com.dreamteam.ssobbi.record.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class UpdateRecordRequest {
    private Integer happinessRate;
    private String content;
    private List<Consumption> consumptions;

    @Getter
    @NoArgsConstructor
    public static class Consumption {
        private String category;
        private Integer amount;
        private Integer targetAmount;
        private Boolean isOverConsumption;
    }
}
