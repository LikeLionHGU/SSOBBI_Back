package com.dreamteam.ssobbi.message.controller;
import com.dreamteam.ssobbi.user.controller.requesst.UserAlarmMessageRequest;
import com.dreamteam.ssobbi.message.service.CoolSMSService;
import com.dreamteam.ssobbi.user.entity.User;
import com.dreamteam.ssobbi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/ssobbi/notifications")
@RequiredArgsConstructor
public class CoolSMSController {

	private final CoolSMSService coolSMSService;

	@PostMapping("/send")
	public ResponseEntity<SingleMessageSentResponse> sendOneAta(@RequestBody UserAlarmMessageRequest userAlarmMessageRequest) {
		return ResponseEntity.ok().body(coolSMSService.sendMessage(userAlarmMessageRequest));
	}

	// 유저가 알람톡 한다고 했을 때, 폰번호 입력하면, user Entity로 들어가서 해당 정보 수정해주는 api 개발 필요
	// 밑의 것을 이용해서 위에 send api 개발 완료하기.

	// 이건 설명을 위한 api 예시임.

	private UserRepository UserRepository;
	@PostMapping("/phone")
	public ResponseEntity<SingleMessageSentResponse> sendOneAta(@AuthenticationPrincipal Long id) {
		User ewra = (User)UserRepository.findById(id).orElseThrow(); // -> service ? dto       :: login한 사람의 정보 가져오기
//		return ResponseEntity.ok().body(coolSMSService.sendMessage(userAlarmMessageRequest));
		return null;
	}

	// 만약 프론트측에서 핸드폰 번호를 받아오게 된다면.. -> 폰 번호만 저장 해두는 DB 하나 만들면 될 듯. @PostMapping
	// 그리고 만약 이제 해당 서비스를 그만 쓰고 싶다고 하면, 삭제하는 API 만들어야할 듯 -> 예시) 알람톡을 그만 받고 싶은 핸드폰 번호를 입력해주세요. (카카오톡과 연결된 핸드폰번호) @DeleteMapping
}
