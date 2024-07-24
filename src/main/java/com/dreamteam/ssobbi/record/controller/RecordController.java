package com.dreamteam.ssobbi.record.controller;

import com.dreamteam.ssobbi.record.controller.request.AddRecordRequest;
import com.dreamteam.ssobbi.record.dto.RecordDto;
import com.dreamteam.ssobbi.record.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
