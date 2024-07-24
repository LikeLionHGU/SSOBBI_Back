package com.dreamteam.ssobbi.message.controller;
import com.dreamteam.ssobbi.message.service.CoolSMSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@Slf4j
@RestController
@RequestMapping("/api/ssobbi/notifications")
@RequiredArgsConstructor
public class CoolSMSController {

	private final CoolSMSService coolSMSService;

	@PostMapping("/send")
	public ResponseEntity<ArrayList<SingleMessageSentResponse>> sendOneAta() {
		return ResponseEntity.ok().body(coolSMSService.sendMessage());
	}
}

// 이 api 수정 필요.