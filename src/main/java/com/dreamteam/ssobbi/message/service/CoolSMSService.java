package com.dreamteam.ssobbi.message.service;

import com.dreamteam.ssobbi.user.dto.UserDto;
import com.dreamteam.ssobbi.user.repository.UserRepository;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.KakaoOption;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import com.dreamteam.ssobbi.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import net.nurigo.sdk.message.model.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CoolSMSService {

	private final DefaultMessageService messageService;

	@Value("${coolsms.template.ID}") String templateId;
	@Value("${coolsms.template.PFID}") String templatePfId;
	@Value("${coolsms.phone.caller}")  String coolSmsPhoneCaller;

	private final UserRepository userRepository;
	private final KakaoOption kakaoOption = new KakaoOption();

	/**
	 * 발급받은 API KEY와 API Secret Key를 사용해주세요.
	 */
	public CoolSMSService(
		@Value("${coolsms.api.key}") String apiKey,
		@Value("${coolsms.api.secret}") String apiSecret,
		UserRepository userRepository
	) {
		this.userRepository = userRepository;
		if (apiKey == null || apiKey.isEmpty() || apiSecret == null || apiSecret.isEmpty()) {
			throw new IllegalArgumentException("API key and secret must be provided");
		}

		this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
	}


	public ArrayList<SingleMessageSentResponse> sendMessage() {
		ArrayList<SingleMessageSentResponse> result = new ArrayList<>();

		kakaoOption.setPfId(templatePfId);
		kakaoOption.setTemplateId(templateId);

		HashMap<String, String> usersMap = getAlarmMessageOkUser();
		if(usersMap.isEmpty()) return null;

		for(Map.Entry<String, String> entry : usersMap.entrySet()) {
			SingleMessageSentResponse eachAlarmStatusResponse = sendMessageEachUser(entry.getKey(), entry.getValue());
			result.add(eachAlarmStatusResponse);
		}

		return result;
	}

	private SingleMessageSentResponse sendMessageEachUser(String key, String value) {
		// 알림톡 템플릿 내에 #{변수} 형태가 존재할 경우 variables를 설정
		HashMap<String, String> variables = new HashMap<>();
		variables.put("#{nickname}", key);
		kakaoOption.setVariables(variables);

		Message message = new Message();
		message.setFrom(coolSmsPhoneCaller);
		message.setTo(value);
		message.setKakaoOptions(kakaoOption);

		SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
		System.out.println(response);

		return response;
	}

	private HashMap<String, String> getAlarmMessageOkUser() {
		ArrayList<UserDto> usersDto = getAllUserDto();
		return getTargetUserInfo(usersDto);
	}

	private HashMap<String, String> getTargetUserInfo(ArrayList<UserDto> usersDto){
		HashMap<String, String> target = new HashMap<>();

		for (UserDto userDto : usersDto) {
			if (userDto.getName() != null && userDto.getPhoneNumber() != null) {
				target.put(userDto.getName(), userDto.getPhoneNumber());
			}
		}
		return target;
	}

	private ArrayList<UserDto> getAllUserDto() {
		List<User> users = userRepository.findAll();
		ArrayList<UserDto> usersDto = new ArrayList<>();

		for (User user : users) {
			usersDto.add(UserDto.from(user));
		}
		return usersDto;
	}

}