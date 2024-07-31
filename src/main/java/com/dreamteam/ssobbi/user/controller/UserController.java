package com.dreamteam.ssobbi.user.controller;

import com.dreamteam.ssobbi.user.controller.request.UserAlarmMessageRequest;
import com.dreamteam.ssobbi.user.controller.request.UserIncomeRequest;
import com.dreamteam.ssobbi.user.controller.response.AboutUserIncomeResponse;
import com.dreamteam.ssobbi.user.controller.response.UpdateUserInfoAboutPhoneResponse;
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

	@PostMapping("/alarm-message/ok")
	public ResponseEntity<UpdateUserInfoAboutPhoneResponse> updatePhoneNumber(@AuthenticationPrincipal Long id, @RequestBody UserAlarmMessageRequest userAlarmMessageRequest) {
		UpdateUserInfoAboutPhoneResponse updateUserInfo = userService.updatePhoneNumber(id, userAlarmMessageRequest);
		return ResponseEntity.ok().body(updateUserInfo);
	}

	@DeleteMapping("/alarm-message/no")
	public ResponseEntity<UpdateUserInfoAboutPhoneResponse> deletePhoneNumber(@AuthenticationPrincipal Long id) {
		UpdateUserInfoAboutPhoneResponse updateUserInfo = userService.deletePhoneNumber(id);
		return ResponseEntity.ok().body(updateUserInfo);
	}

	@PostMapping("/monthly/income") // 이걸로 patch도 가능
	public ResponseEntity<AboutUserIncomeResponse> updateIncome(@AuthenticationPrincipal Long id, @RequestBody UserIncomeRequest userIncomeRequest) {
		AboutUserIncomeResponse updateUserIncome = userService.updateIncome(id, userIncomeRequest);
		return ResponseEntity.ok().body(updateUserIncome);
	}

	@GetMapping("/monthly/income")
	public ResponseEntity<AboutUserIncomeResponse> getIncome(@AuthenticationPrincipal Long id) {
		AboutUserIncomeResponse updateUserIncome = userService.getIncome(id);
		return ResponseEntity.ok().body(updateUserIncome);
	}

}

