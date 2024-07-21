package com.dreamteam.ssobbi.message.service;

import com.dreamteam.ssobbi.user.controller.requesst.UserAlarmMessageRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.KakaoOption;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import com.dreamteam.ssobbi.user.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import net.nurigo.sdk.message.model.Message;

import java.util.HashMap;


@Service
@RequiredArgsConstructor
public class CoolSMSService {

	@Value("${coolsms.template.ID}")
	private String coolSmsTemplateId;

	@Value("${coolsms.template.PFID}")
	private String coolSmsTemplatePfId;

	@Value("${coolsms.api.key}")
	private String coolSmsApiKey;

	@Value("${coolsms.api.secret}")
	private String coolSmsApiSecret;

	@Value("${coolsms.phone.caller}")
	private String coolSmsPhoneCaller;

	private DefaultMessageService messageService;

	@PostConstruct
	public void init() {
		this.messageService = NurigoApp.INSTANCE.initialize(coolSmsApiKey, coolSmsApiSecret, "https://api.coolsms.co.kr");
	}

	public SingleMessageSentResponse sendMessage(UserAlarmMessageRequest userAlarmMessageRequest) {
		KakaoOption kakaoOption = new KakaoOption();

		kakaoOption.setPfId(coolSmsTemplateId);
		kakaoOption.setTemplateId(coolSmsTemplatePfId);

		// 알림톡 템플릿 내에 #{변수} 형태가 존재할 경우 variables를 설정
		HashMap<String, String> variables = new HashMap<>();
		variables.put("#{nickname}", getCurrentUserName());
		kakaoOption.setVariables(variables);

		Message message = new Message();
		// todo : 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되도록 구현
		message.setFrom(coolSmsPhoneCaller);
		message.setTo(userAlarmMessageRequest.getUserPhoneNumber());
		message.setKakaoOptions(kakaoOption);

		SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
		System.out.println(response);

		return response;
	}

	private String getCurrentUserName() {
		// User Entity에서 모든 정보 가져와서, map으로 이름 - 폰 번호 연결

    // 현재 로그인 하고 있는 사람 이름
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof User) {
			User user = (User) principal;
			return user.getName();
		}
		return null;
	}

}

