package com.dreamteam.ssobbi.message.controller;
import com.dreamteam.ssobbi.message.service.CoolSMSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/ssobbi/notifications")
@RequiredArgsConstructor
public class CoolSMSController {

	private final CoolSMSService coolSMSService;

	@PostMapping("/send")
	public SingleMessageSentResponse sendOneAta() {
		return coolSMSService.sendMessage();
	}
}
