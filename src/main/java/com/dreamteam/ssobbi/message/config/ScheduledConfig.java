package com.dreamteam.ssobbi.message.config;

import com.dreamteam.ssobbi.message.entity.ScheduledMessage;
import com.dreamteam.ssobbi.message.service.KakaoMessageService;
import com.dreamteam.ssobbi.message.service.ScheduledMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class ScheduledConfig {

	private final ScheduledMessageService scheduledMessageService;
	private final KakaoMessageService kakaoMessageService;  // temporary use

	@Scheduled(fixedRate = 60000) // 60,000 milliseconds (1 min)
	public void sendScheduledMessages() {
		LocalDateTime now = LocalDateTime.now();
		List<ScheduledMessage> messages = scheduledMessageService.getScheduledMessages();
		for (ScheduledMessage message : messages) {
			if (message.getScheduledTime().isBefore(now) || message.getScheduledTime().isEqual(now)) {
				// 카카오톡 메시지 전송
				kakaoMessageService.sendKakaoMessage(message.getRecipient(), message.getMessage());

//				scheduledMessageService.delete(message);    // if send it, delete from the db
			}
		}

	}
}


