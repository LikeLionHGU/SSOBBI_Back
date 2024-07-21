package com.dreamteam.ssobbi.user.service;

import com.dreamteam.ssobbi.base.exception.NotFoundException;
import com.dreamteam.ssobbi.user.controller.requesst.UserAlarmMessageRequest;
import com.dreamteam.ssobbi.user.controller.response.UpdateUserInfoAboutPhone;
import com.dreamteam.ssobbi.user.dto.UserDto;
import com.dreamteam.ssobbi.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	@Transactional
	public UpdateUserInfoAboutPhone updatePhoneNumber(Long id, UserAlarmMessageRequest userAlarmMessageRequest) {
		UserDto updateUserDto = UserDto.from(userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found in DB.")));
		updateUserDto.setPhoneNumber(userAlarmMessageRequest.getUserPhoneNumber());

		return new UpdateUserInfoAboutPhone(updateUserDto.getName(), updateUserDto.getPhoneNumber());
	}

	@Transactional
	public UpdateUserInfoAboutPhone deletePhoneNumber(Long id) {
		UserDto updateUserDto = UserDto.from(userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found in DB.")));
		updateUserDto.setPhoneNumber(null);

		return new UpdateUserInfoAboutPhone(updateUserDto.getName(), updateUserDto.getPhoneNumber());
	}
}
