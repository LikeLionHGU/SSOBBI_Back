package com.dreamteam.ssobbi.record.controller;

import com.dreamteam.ssobbi.record.controller.request.AddRecordRequest;
import com.dreamteam.ssobbi.record.controller.request.UpdateRecordRequest;
import com.dreamteam.ssobbi.record.controller.response.DailyRecordResponse;
import com.dreamteam.ssobbi.record.dto.RecordDto;
import com.dreamteam.ssobbi.record.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class RecordController {
  private final RecordService recordService;

  @PostMapping("/api/ssobbi/records")
  public ResponseEntity<Void> addRecord(
      @RequestBody AddRecordRequest request, @AuthenticationPrincipal Long id) {
    recordService.addRecord(RecordDto.from(request), id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/api/ssobbi/records/daily/{date}")
  public ResponseEntity<DailyRecordResponse> getDailyRecord(
      @PathVariable LocalDate date, @AuthenticationPrincipal Long id) {
    return ResponseEntity.ok(DailyRecordResponse.from(recordService.getDailyRecord(date, id)));
  }

  @PutMapping("/api/ssobbi/records/{id}")
  public ResponseEntity<Void> updateRecord(
      @PathVariable Long id,
      @RequestBody UpdateRecordRequest request,
      @AuthenticationPrincipal Long userId) {
    recordService.updateRecord(id, RecordDto.from(request), userId);
    return ResponseEntity.ok().build();
  }
}
