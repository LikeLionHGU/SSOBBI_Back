package com.dreamteam.ssobbi.record.service;

import com.dreamteam.ssobbi.base.exception.NotFoundException;
import com.dreamteam.ssobbi.monthlyTargetAmount.repository.MonthlyTargetAmountRepository;
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
	private final MonthlyTargetAmountRepository monthlyTargetAmountRepository;

	public ConsumptionService(
		ConsumptionRepository consumptionRepository,
		RecordRepository recordRepository,
		UserRepository userRepository,
		MonthlyTargetAmountService monthlyTargetAmountService, MonthlyTargetAmountRepository monthlyTargetAmountRepository) {
		this.consumptionRepository = consumptionRepository;
		this.recordRepository = recordRepository;
		this.userRepository = userRepository;
		this.monthlyTargetAmountService = monthlyTargetAmountService;
		this.monthlyTargetAmountRepository = monthlyTargetAmountRepository;
	}

	public MonthlyConsumptionsAndTargetsByCategoryResponse getMonthlyCategoryConsumption(Long userId, LocalDate date) {

		ArrayList<Record> records =
			recordRepository.findByUser((userRepository.findById(userId)).orElseThrow(() -> new NotFoundException("해당 유저가 존재하지 않습니다.")));

		ArrayList<ArrayList<Consumption>> monthlyAllConsumption = getMonthlyConsumption(records, date);
		ArrayList<String> categoryList = monthlyTargetAmountService.getMonthlyTargetAmountByCategory(userId).getCategories();

		ArrayList<MonthlyConsumptionsAndTargetsByCategoryResponse.MonthlyConsumptionsAndTargetsByCategory> response =
			getMonthlyConsumptionsAndTargetsByCategory(monthlyAllConsumption, categoryList);


		return MonthlyConsumptionsAndTargetsByCategoryResponse.builder()
			.userIncome(userRepository.findById(userId).orElseThrow(()-> new NotFoundException("유저 정보가 없습니다.")).getIncome())
			.monthlyConsumptionsAndTargetsByCategory(response)
			.build();

	}

	private ArrayList<MonthlyConsumptionsAndTargetsByCategoryResponse.MonthlyConsumptionsAndTargetsByCategory> getMonthlyConsumptionsAndTargetsByCategory(
		ArrayList<ArrayList<Consumption>> monthlyAllConsumption,
		ArrayList<String> categoryList) {

		HashMap<String, Integer> amountByCategory = getAmountByCategory(categoryList, monthlyAllConsumption);

		HashMap<String, Integer> targetAmount = getTargetAmount(categoryList);

		ArrayList<MonthlyConsumptionsAndTargetsByCategoryResponse.MonthlyConsumptionsAndTargetsByCategory> response =
			getMonthlyConsumptionsAndTargetsByCategoryList(amountByCategory, targetAmount);

//		response.add(MonthlyConsumptionsAndTargetsByCategoryResponse
//			.MonthlyConsumptionsAndTargetsByCategory.from(amountByCategory.get(0), amountByCategory.get(1), getTargetAmount(category)));

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

	private HashMap<String, Integer> getTargetAmount(ArrayList<String> categoryList) {
		HashMap<String, Integer> targetAmount = new HashMap<>();
		for(String category : categoryList) {
			targetAmount.put(category, monthlyTargetAmountRepository.findByCategory(category).getAmount());
		}
		return targetAmount;
	}

	private HashMap<String, Integer> getAmountByCategory(ArrayList<String> categoryList, ArrayList<ArrayList<Consumption>> monthlyAllConsumption) {
		HashMap<String, Integer> amountByCategoryRes = new HashMap<>();

		for(String category : categoryList) {
			int categorySum = 0;
			for(ArrayList<Consumption> consumptions : monthlyAllConsumption) {
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

	private ArrayList<ArrayList<Consumption>> getMonthlyConsumption(ArrayList<Record> records, LocalDate date) {
		ArrayList<ArrayList<Consumption>> monthlyAllConsumption = new ArrayList<>();
		for (Record record : records) {
			if (record.getDate().getMonth() == date.getMonth()) {
				monthlyAllConsumption.add(consumptionRepository.findByRecord(record));
			}
		}
		return monthlyAllConsumption;
	}

}


