package com.dreamteam.ssobbi.message.config;

import com.dreamteam.ssobbi.message.entity.ScheduledMessage;
import com.dreamteam.ssobbi.message.service.KakaoMessageService;
import com.dreamteam.ssobbi.message.service.ScheduledMessageService;
import com.dreamteam.ssobbi.user.entity.User;
import com.dreamteam.ssobbi.user.repository.UserRepository;
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
	private final UserRepository userRepository;

//	@Scheduled(fixedRate = 60000) // 60,000 milliseconds (1 min)
//	public void sendScheduledMessages() {
//		LocalDateTime now = LocalDateTime.now();
//		List<ScheduledMessage> messages = scheduledMessageService.getScheduledMessages();
//		for (ScheduledMessage message : messages) {
//			if (message.getScheduledTime().isBefore(now) || message.getScheduledTime().isEqual(now)) {
//				kakaoMessageService.sendKakaoMessage(message.getRecipient(), message.getMessage());
//
////				scheduledMessageService.delete(message);    // if send it, delete from the db
//			}
//		}
//	}

	// 매일 21시에 실행 -> test를 위해 16시 45분       -> User에서 kakao_id를 읽
	@Scheduled(cron = "0 17 17 * * ?", zone = "Asia/Seoul")
	public void sendMessagesInStaticTime() {
		List<User> users = userRepository.findAll();
		for (User user : users) {
			// 카카오톡 메시지 전송
			kakaoMessageService.sendKakaoMessage(String.valueOf(user.getKakaoId()), "우리 서비스를 이용하세요: http://localhost:3000");
		}
	}

}


