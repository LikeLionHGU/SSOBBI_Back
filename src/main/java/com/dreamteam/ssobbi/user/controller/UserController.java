package com.dreamteam.ssobbi.user.controller;

import com.dreamteam.ssobbi.user.controller.requesst.UserAlarmMessageRequest;
import com.dreamteam.ssobbi.user.controller.response.UpdateUserInfoAboutPhone;
import com.dreamteam.ssobbi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ssobbi/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/alarmMessage/Ok")
	public ResponseEntity<UpdateUserInfoAboutPhone> updatePhoneNumber(@AuthenticationPrincipal Long userId, @RequestBody UserAlarmMessageRequest userAlarmMessageRequest) {
		UpdateUserInfoAboutPhone updateUserInfo = userService.updatePhoneNumber(userId, userAlarmMessageRequest);
		return ResponseEntity.ok().body(updateUserInfo);
	}

}

