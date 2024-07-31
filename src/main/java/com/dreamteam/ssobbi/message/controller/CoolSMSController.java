package com.dreamteam.ssobbi.message.controller;
import com.dreamteam.ssobbi.message.config.ScheduledConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/ssobbi/notifications")
public class CoolSMSController {

	private final ScheduledConfig scheduledConfig;

	public CoolSMSController(ScheduledConfig scheduledConfig) {
		this.scheduledConfig = scheduledConfig;
	}

	@PostMapping("/send")
	public ResponseEntity<Void> sendOneAta() {
		scheduledConfig.sendDailyMessage();
		return ResponseEntity.ok().build(); //body(scheduledConfig.sendDailyMessage());
	}
}

// 이 api 수정 필요.