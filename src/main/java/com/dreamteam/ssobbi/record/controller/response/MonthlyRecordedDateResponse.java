package com.dreamteam.ssobbi.record.controller.response;

import com.dreamteam.ssobbi.record.dto.RecordDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class MonthlyRecordedDateResponse {
  private List<LocalDate> recordedDates;

  public static MonthlyRecordedDateResponse from(List<RecordDto> dtos) {
    return MonthlyRecordedDateResponse.builder()
        .recordedDates(dtos.stream().map(RecordDto::getDate).toList())
        .build();
  }
}
