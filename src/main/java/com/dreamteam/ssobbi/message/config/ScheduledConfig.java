package com.dreamteam.ssobbi.message.config;

import com.dreamteam.ssobbi.message.service.CoolSMSService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Configuration
@Component
@RequiredArgsConstructor
public class ScheduledConfig {

	private final CoolSMSService coolSMSService;

	@Scheduled(cron = "0 0 21 * * *", zone = "Asia/Seoul")
	public void sendDailyMessage() {
		coolSMSService.sendMessage();
	}
}

