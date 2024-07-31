package com.dreamteam.ssobbi.record.service;

import com.dreamteam.ssobbi.base.exception.NotFoundException;
import com.dreamteam.ssobbi.monthlyTargetAmount.service.MonthlyTargetAmountService;
import com.dreamteam.ssobbi.record.controller.response.MonthlyConsumptionsAndTargetsByCategoryResponse;
import com.dreamteam.ssobbi.record.entity.Consumption;
import com.dreamteam.ssobbi.record.entity.Record;
import com.dreamteam.ssobbi.record.repository.ConsumptionRepository;
import com.dreamteam.ssobbi.record.repository.RecordRepository;
import com.dreamteam.ssobbi.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ConsumptionService {

	private final ConsumptionRepository consumptionRepository;
	private final RecordRepository recordRepository;
	private final UserRepository userRepository;
	private final MonthlyTargetAmountService monthlyTargetAmountService;

	public ConsumptionService(
		ConsumptionRepository consumptionRepository,
		RecordRepository recordRepository,
		UserRepository userRepository,
		MonthlyTargetAmountService monthlyTargetAmountService) {
		this.consumptionRepository = consumptionRepository;
		this.recordRepository = recordRepository;
		this.userRepository = userRepository;
		this.monthlyTargetAmountService = monthlyTargetAmountService;
	}

	public MonthlyConsumptionsAndTargetsByCategoryResponse getMonthlyCategoryConsumption(Long userId, LocalDate date) {

		ArrayList<Record> records =	recordRepository.findByUser((userRepository.findById(userId)).orElseThrow(() -> new NotFoundException("해당 유저가 존재하지 않습니다.")));

		ArrayList<ArrayList<Consumption>> userMonthlyConsumptions = getMonthlyConsumption(records, date);

		ArrayList<String> categoryList = monthlyTargetAmountService.getCategoryList(userId).getCategories();

		ArrayList<MonthlyConsumptionsAndTargetsByCategoryResponse.MonthlyConsumptionsAndTargetsByCategory> response =
			getMonthlyConsumptionsAndTargetsByCategory(userMonthlyConsumptions, categoryList);

		// 예외 처리 cofactor 필요.
		if(userRepository.findById(userId).orElseThrow(()-> new NotFoundException("유저 정보가 없습니다.")).getIncome() == null)
			throw new NotFoundException("Income 정보를 찾을 수 없습니다. (income 등륵 해주세요)");

		return MonthlyConsumptionsAndTargetsByCategoryResponse.builder()
			.userIncome(userRepository.findById(userId).orElseThrow(()-> new NotFoundException("유저 정보가 없습니다.")).getIncome())
			.monthlyConsumptionsAndTargetsByCategory(response)
			.build();
	}

	private ArrayList<MonthlyConsumptionsAndTargetsByCategoryResponse.MonthlyConsumptionsAndTargetsByCategory> getMonthlyConsumptionsAndTargetsByCategory(
		ArrayList<ArrayList<Consumption>> userMonthlyConsumptions,
		ArrayList<String> categoryList) {

		HashMap<String, Integer> amountByCategory = getAmountByCategory(categoryList, userMonthlyConsumptions);

		HashMap<String, Integer> targetAmount = MonthlyTargetAmountService.getAmountAndCategory();

		ArrayList<MonthlyConsumptionsAndTargetsByCategoryResponse.MonthlyConsumptionsAndTargetsByCategory> response =
			getMonthlyConsumptionsAndTargetsByCategoryList(amountByCategory, targetAmount);

		return response;
	}

	private ArrayList<MonthlyConsumptionsAndTargetsByCategoryResponse.MonthlyConsumptionsAndTargetsByCategory> getMonthlyConsumptionsAndTargetsByCategoryList
		(HashMap<String, Integer> amountByCategory, HashMap<String, Integer> targetAmount) {

		ArrayList<MonthlyConsumptionsAndTargetsByCategoryResponse.MonthlyConsumptionsAndTargetsByCategory> response = new ArrayList<>();

		for(Map.Entry<String, Integer> entry : amountByCategory.entrySet()) {
			response.add(MonthlyConsumptionsAndTargetsByCategoryResponse
				.MonthlyConsumptionsAndTargetsByCategory.from(entry.getKey(), entry.getValue(), targetAmount.get(entry.getKey())));
		}

		return response;
	}

	private HashMap<String, Integer> getAmountByCategory(ArrayList<String> categoryList, ArrayList<ArrayList<Consumption>> userMonthlyConsumptions) {
		HashMap<String, Integer> amountByCategoryRes = new HashMap<>();

		for(String category : categoryList) {
			int categorySum = 0;
			for(ArrayList<Consumption> consumptions : userMonthlyConsumptions) {
				for(Consumption consumption : consumptions) {
					if(consumption.getCategory().equals(category)) {
						categorySum += consumption.getAmount();
					}
				}
			}
			amountByCategoryRes.put(category, categorySum);
		}

		return amountByCategoryRes;
	}

	private ArrayList<ArrayList<Consumption>> getMonthlyConsumption(ArrayList<Record> userRecords, LocalDate date) {
		ArrayList<ArrayList<Consumption>> userMonthlyConsumptions = new ArrayList<>();
		for (Record record : userRecords) {
			if (record.getDate().getMonth() == date.getMonth()) {
				userMonthlyConsumptions.add(consumptionRepository.findByRecord(record));
			}
		}
		return userMonthlyConsumptions;
	}

}


