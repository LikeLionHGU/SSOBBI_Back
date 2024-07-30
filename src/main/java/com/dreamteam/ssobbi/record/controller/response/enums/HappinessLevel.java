package com.dreamteam.ssobbi.record.controller.response.enums;

import lombok.Getter;

@Getter
public enum HappinessLevel {
    LOW("낮음"),
    NORMAL("중간"),
    HIGH("높음");

    private final String korean;

    HappinessLevel(String korean) {
        this.korean = korean;
    }

    public static HappinessLevel from(Integer averageHappinessRate, Integer happinessRate, Integer standardDeviation) {
        if (happinessRate < averageHappinessRate - standardDeviation*0.7) {
            return LOW;
        } else if (happinessRate > averageHappinessRate + standardDeviation*0.7) {
            return HIGH;
        } else {
            return NORMAL;
        }
    }
}
