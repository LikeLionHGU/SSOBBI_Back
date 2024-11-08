package com.dreamteam.ssobbi.user.service;

import com.dreamteam.ssobbi.base.exception.NotFoundException;
import com.dreamteam.ssobbi.user.controller.request.UserAlarmMessageRequest;
import com.dreamteam.ssobbi.user.controller.request.UserIncomeRequest;
import com.dreamteam.ssobbi.user.controller.response.AboutUserCategoryConsumptionResponse;
import com.dreamteam.ssobbi.user.controller.response.AboutUserIncomeResponse;
import com.dreamteam.ssobbi.user.controller.response.UpdateUserInfoAboutPhoneResponse;
import com.dreamteam.ssobbi.user.dto.UserDto;
import com.dreamteam.ssobbi.user.entity.User;
import com.dreamteam.ssobbi.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	@Transactional
	public UpdateUserInfoAboutPhoneResponse updatePhoneNumber(Long id, UserAlarmMessageRequest userAlarmMessageRequest) {
		User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("유저 정보가 DB에 없습니다."));
		user.setPhoneNumber(userAlarmMessageRequest.getUserPhoneNumber());

		return new UpdateUserInfoAboutPhoneResponse(user.getName(), user.getPhoneNumber());
	}

	@Transactional
	public UpdateUserInfoAboutPhoneResponse deletePhoneNumber(Long id) {
		User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("유저 정보가 DB에 없습니다."));
		user.setPhoneNumber(null);

		return new UpdateUserInfoAboutPhoneResponse(user.getName(), user.getPhoneNumber());
	}

	@Transactional
	public AboutUserIncomeResponse updateIncome(Long id, UserIncomeRequest userIncomeRequest) {
		User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("유저 정보가 DB에 없습니다"));
		user.setIncome(userIncomeRequest.getIncome());

		return new AboutUserIncomeResponse(user.getName(), user.getIncome());
	}

	public AboutUserIncomeResponse getIncome(Long id) {
		User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("유저 정보가 DB에 없습니다"));
		return new AboutUserIncomeResponse(user.getName(), user.getIncome());
	}

	public AboutUserCategoryConsumptionResponse getListAboutUserCategoryConsumption(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(()-> new NotFoundException("유저 정보가 DB에 없습니다"));

		ArrayList<AboutUserCategoryConsumptionResponse.userCategoryAndAmount> userCategoryAndAmounts = makeTempUserCategoryAndAmountList(user);

	    return AboutUserCategoryConsumptionResponse.builder().userCategoryAndAmounts(userCategoryAndAmounts).build();
	}

	private ArrayList<AboutUserCategoryConsumptionResponse.userCategoryAndAmount> makeTempUserCategoryAndAmountList(User user) {
		ArrayList<AboutUserCategoryConsumptionResponse.userCategoryAndAmount> userCategoryAndAmounts = new ArrayList<>();
		userCategoryAndAmounts.add(new AboutUserCategoryConsumptionResponse.userCategoryAndAmount("식비", 0));
		userCategoryAndAmounts.add(new AboutUserCategoryConsumptionResponse.userCategoryAndAmount("교통", 0));
		userCategoryAndAmounts.add(new AboutUserCategoryConsumptionResponse.userCategoryAndAmount("문화", 0));
		userCategoryAndAmounts.add(new AboutUserCategoryConsumptionResponse.userCategoryAndAmount("기타", user.getIncome()));
		return userCategoryAndAmounts;
	}

	public UserDto getUser(Long id) {
		User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("유저 정보가 DB에 없습니다"));
		return UserDto.from(user);
	}
}
