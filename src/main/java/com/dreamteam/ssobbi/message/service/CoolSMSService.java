package com.dreamteam.ssobbi.message.service;

import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.KakaoOption;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
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

	private final DefaultMessageService messageService;

	public CoolSMSService() {
		this.messageService = NurigoApp.INSTANCE.initialize(coolSmsApiKey, coolSmsApiSecret, "https://api.coolsms.co.kr");
	}


	public SingleMessageSentResponse sendMessage() {
		KakaoOption kakaoOption = new KakaoOption();

		kakaoOption.setPfId(coolSmsTemplateId);
		kakaoOption.setTemplateId(coolSmsTemplatePfId);

		// 알림톡 템플릿 내에 #{변수} 형태가 존재할 경우 variables를 설정
		HashMap<String, String> variables = new HashMap<>();
//		variables.put("#{nickname}", User.name);
		kakaoOption.setVariables(variables);

		Message message = new Message();
		// todo : 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되도록 구현
		message.setFrom("발신번호 입력");
		message.setTo("수신번호 입력");
		message.setKakaoOptions(kakaoOption);

		SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
		System.out.println(response);

		return response;
	}
}
