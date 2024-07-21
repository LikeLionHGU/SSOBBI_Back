package com.dreamteam.ssobbi.user.controller;

import com.dreamteam.ssobbi.user.controller.requesst.UserAlarmMessageRequest;
import com.dreamteam.ssobbi.user.controller.response.UpdateUserInfoAboutPhone;
import com.dreamteam.ssobbi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ssobbi/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/alarmMessage/Ok")
	public ResponseEntity<UpdateUserInfoAboutPhone> updatePhoneNumber(@AuthenticationPrincipal Long id, @RequestBody UserAlarmMessageRequest userAlarmMessageRequest) {
		UpdateUserInfoAboutPhone updateUserInfo = userService.updatePhoneNumber(id, userAlarmMessageRequest);
		return ResponseEntity.ok().body(updateUserInfo);
	}

	@DeleteMapping("/alarmMessage/No")
	public ResponseEntity<UpdateUserInfoAboutPhone> deletePhoneNumber(@AuthenticationPrincipal Long id) {
		UpdateUserInfoAboutPhone updateUserInfo = userService.deletePhoneNumber(id);
		return ResponseEntity.ok().body(updateUserInfo);
	}

}

