package com.dreamteam.ssobbi.monthlyTargetAmount.service;

import com.dreamteam.ssobbi.base.exception.NotFoundException;
import com.dreamteam.ssobbi.monthlyTargetAmount.controller.reponse.CategoryMonthlyTargetAmountCategoryResponse;
import com.dreamteam.ssobbi.monthlyTargetAmount.controller.reponse.CategoryMonthlyTargetAmountResponse;
import com.dreamteam.ssobbi.monthlyTargetAmount.controller.request.CategoryMonthlyTargetAmountRequest;
import com.dreamteam.ssobbi.monthlyTargetAmount.entity.MonthlyTargetAmount;
import com.dreamteam.ssobbi.monthlyTargetAmount.exception.DuplicateValueException;
import com.dreamteam.ssobbi.monthlyTargetAmount.exception.NegativeValueException;
import com.dreamteam.ssobbi.monthlyTargetAmount.exception.NullValueException;
import com.dreamteam.ssobbi.monthlyTargetAmount.repository.MonthlyTargetAmountRepository;
import com.dreamteam.ssobbi.user.entity.User;
import com.dreamteam.ssobbi.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MonthlyTargetAmountService {

	private final UserRepository userRepository;
	private final MonthlyTargetAmountRepository monthlyTargetAmountRepository;

	public MonthlyTargetAmountService(UserRepository userRepository, MonthlyTargetAmountRepository monthlyTargetAmountRepository) {
		this.userRepository = userRepository;
		this.monthlyTargetAmountRepository = monthlyTargetAmountRepository;
	}

	public void saveMonthlyTargetAmount(Long id, ArrayList<CategoryMonthlyTargetAmountRequest> requests) {
		User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("유저 정보가 DB에 없습니다."));

		CheckException(requests);
		CheckDuplicateCategoryAboutDBAndInput(requests);

		for(CategoryMonthlyTargetAmountRequest request : requests) {
			MonthlyTargetAmount monthlyTargetAmount = monthlyTargetAmountRepository.save(MonthlyTargetAmount.builder()
				.category(request.getCategory())
				.amount(request.getAmount())
				.user(user)
				.build());
		}
	}

	@Transactional
	public void updateMonthlyTargetAmount(Long userId, ArrayList<CategoryMonthlyTargetAmountRequest> request) {
		User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("유저 정보가 DB에 없습니다."));

		CheckException(request);

		for(CategoryMonthlyTargetAmountRequest categoryMonthlyTargetAmountRequest : request) {
			MonthlyTargetAmount monthlyTargetAmount = (MonthlyTargetAmount) monthlyTargetAmountRepository.findByUserAndCategory(user, categoryMonthlyTargetAmountRequest.getCategory())
				.orElseThrow(() -> new NotFoundException("해당 카테고리의 목표 금액이 DB에 없습니다."));
			monthlyTargetAmount.setAmount(categoryMonthlyTargetAmountRequest.getAmount());
			monthlyTargetAmountRepository.save(monthlyTargetAmount);
		}
	}

	public CategoryMonthlyTargetAmountResponse getMonthlyTargetAmount(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("유저 정보가 DB에 없습니다."));
		ArrayList<MonthlyTargetAmount> monthlyTargetAmounts = monthlyTargetAmountRepository.findByUser(user);

		return new CategoryMonthlyTargetAmountResponse(convertToCategoryMonthlyTargetAmountRequest(monthlyTargetAmounts));
	}

	private ArrayList<CategoryMonthlyTargetAmountRequest> convertToCategoryMonthlyTargetAmountRequest(ArrayList<MonthlyTargetAmount> monthlyTargetAmounts) {
		ArrayList<CategoryMonthlyTargetAmountRequest> categoryMonthlyTargetAmountRequests = new ArrayList<>();
		for(MonthlyTargetAmount monthlyTargetAmount : monthlyTargetAmounts) {
			categoryMonthlyTargetAmountRequests.add(new CategoryMonthlyTargetAmountRequest(monthlyTargetAmount.getCategory(), monthlyTargetAmount.getAmount()));
		}
		return categoryMonthlyTargetAmountRequests;
	}


	private void CheckException(ArrayList<CategoryMonthlyTargetAmountRequest> requests) {
		CheckInputNull(requests);
		CheckInputNegative(requests);
		CheckDuplicateCategoryAboutInput(requests);
	}

	private void CheckDuplicateCategoryAboutDBAndInput(ArrayList<CategoryMonthlyTargetAmountRequest> requests) {
		for(CategoryMonthlyTargetAmountRequest request : requests) {
			if(monthlyTargetAmountRepository.existsByCategory(request.getCategory())) {
				throw new DuplicateValueException("DB에 이미 존재하는 category가 있습니다.");
			}
		}
	}

	private void CheckInputNegative(ArrayList<CategoryMonthlyTargetAmountRequest> requests) {
		for (CategoryMonthlyTargetAmountRequest request : requests) {
			if (request.getAmount() < 0) {
				throw new NegativeValueException("목표 금액으로 음수가 입력되었습니다.");
			}
		}
	}

	private void CheckInputNull(ArrayList<CategoryMonthlyTargetAmountRequest> requests) {
		for(CategoryMonthlyTargetAmountRequest request : requests) {
			if(request.getCategory() == null) {
				throw new NullValueException("category가 입력되지 않았습니다.");
			}
		}
	}

	private void CheckDuplicateCategoryAboutInput(ArrayList<CategoryMonthlyTargetAmountRequest> requests) {
		for(int i = 0; i < requests.size(); i++) {
			for(int j = i + 1; j < requests.size(); j++) {
				if(requests.get(i).getCategory().equals(requests.get(j).getCategory())) {
					throw new DuplicateValueException("중복된 category가 있습니다.");
				}
			}
		}
	}


	public CategoryMonthlyTargetAmountCategoryResponse getMonthlyTargetAmountByCategory(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("유저 정보가 DB에 없습니다."));

		ArrayList<MonthlyTargetAmount> monthlyTargetAmounts = monthlyTargetAmountRepository.findByUser(user);

		return getMonthlyTargetAmountByCategoryList(monthlyTargetAmounts);

	}

	private CategoryMonthlyTargetAmountCategoryResponse getMonthlyTargetAmountByCategoryList(ArrayList<MonthlyTargetAmount> monthlyTargetAmounts) {
		ArrayList<String> categoryMonthlyTargetAmountResponse = new ArrayList<>();

		for(MonthlyTargetAmount monthlyTargetAmount : monthlyTargetAmounts) {
			categoryMonthlyTargetAmountResponse.add(monthlyTargetAmount.getCategory());
		}

		return new CategoryMonthlyTargetAmountCategoryResponse(categoryMonthlyTargetAmountResponse);
	}
}

