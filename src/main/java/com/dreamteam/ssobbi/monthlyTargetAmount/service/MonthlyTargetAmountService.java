package com.dreamteam.ssobbi.monthlyTargetAmount.service;

import com.dreamteam.ssobbi.monthlyTargetAmount.controller.reponse.CategoryMonthlyTargetAmountResponse;
import com.dreamteam.ssobbi.monthlyTargetAmount.controller.request.CategoryMonthlyTargetAmountRequest;
import com.dreamteam.ssobbi.monthlyTargetAmount.dto.MonthlyTargetAmountDto;
import com.dreamteam.ssobbi.monthlyTargetAmount.entity.MonthlyTargetAmount;
import com.dreamteam.ssobbi.monthlyTargetAmount.repository.MonthlyTargetAmountRepository;
import com.dreamteam.ssobbi.user.entity.User;
import com.dreamteam.ssobbi.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MonthlyTargetAmountService {

	private final UserRepository userRepository;
	private final MonthlyTargetAmountRepository monthlyTargetAmountRepository;

	public MonthlyTargetAmountService(UserRepository userRepository, MonthlyTargetAmountRepository monthlyTargetAmountRepository) {
		this.userRepository = userRepository;
		this.monthlyTargetAmountRepository = monthlyTargetAmountRepository;
	}

	public CategoryMonthlyTargetAmountResponse saveMonthlyTargetAmount(Long id, List<CategoryMonthlyTargetAmountRequest> requests) {
		User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

		ArrayList<MonthlyTargetAmountDto> responses = new ArrayList<>();

		for(CategoryMonthlyTargetAmountRequest request : requests) {
			MonthlyTargetAmount monthlyTargetAmount = monthlyTargetAmountRepository.save(MonthlyTargetAmount.builder()
					.category(request.getCategory())
					.amount(request.getAmount())
					.user(user)
					.build());
			responses.add(MonthlyTargetAmountDto.from(monthlyTargetAmount));
		}

		return new CategoryMonthlyTargetAmountResponse(user.getName(), responses);
	}
}

