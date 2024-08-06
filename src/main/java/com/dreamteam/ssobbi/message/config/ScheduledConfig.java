package com.dreamteam.ssobbi.message.config;
import com.dreamteam.ssobbi.message.service.CoolSMSService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class ScheduledConfig {

	private final CoolSMSService coolSMSService;

	public ScheduledConfig(CoolSMSService coolSMSService) {
		this.coolSMSService = coolSMSService;
	}

	@Scheduled(cron = "0 0 21 * * *", zone = "Asia/Seoul")
	public void sendDailyMessage() {
		coolSMSService.sendMessage();
	}
}