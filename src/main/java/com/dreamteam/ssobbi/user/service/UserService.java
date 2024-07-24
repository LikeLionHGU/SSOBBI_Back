package com.dreamteam.ssobbi.user.service;

import com.dreamteam.ssobbi.base.exception.NotFoundException;
import com.dreamteam.ssobbi.user.controller.requesst.UserAlarmMessageRequest;
import com.dreamteam.ssobbi.user.controller.response.UpdateUserInfoAboutPhone;
import com.dreamteam.ssobbi.user.dto.UserDto;
import com.dreamteam.ssobbi.user.entity.User;
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
		User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found in DB."));
		user.setPhoneNumber(userAlarmMessageRequest.getUserPhoneNumber());
		UpdateUserInfoAboutPhone updateUserDto = new UpdateUserInfoAboutPhone(user.getName(), user.getPhoneNumber());

		return new UpdateUserInfoAboutPhone(updateUserDto.getName(), updateUserDto.getPhoneNumber());
	}

	@Transactional
	public UpdateUserInfoAboutPhone deletePhoneNumber(Long id) {
		User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found in DB."));
		user.setPhoneNumber(null);
		UpdateUserInfoAboutPhone updateUserDto = new UpdateUserInfoAboutPhone(user.getName(), user.getPhoneNumber());

		return new UpdateUserInfoAboutPhone(updateUserDto.getName(), updateUserDto.getPhoneNumber());
	}
}
