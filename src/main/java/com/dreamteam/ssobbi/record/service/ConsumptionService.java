package com.dreamteam.ssobbi.record.service;

import com.dreamteam.ssobbi.base.exception.NotFoundException;
import com.dreamteam.ssobbi.monthlyTargetAmount.service.MonthlyTargetAmountService;
import com.dreamteam.ssobbi.record.controller.response.MonthlyConsumptionsAndTargetsByCategoryResponse;
import com.dreamteam.ssobbi.record.entity.Consumption;
import com.dreamteam.ssobbi.record.entity.Record;
import com.dreamteam.ssobbi.record.repository.ConsumptionRepository;
import com.dreamteam.ssobbi.record.repository.RecordRepository;
import com.dreamteam.ssobbi.user.repository.UserRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

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

		ArrayList<Record> records =
			recordRepository.findByUser((userRepository.findById(userId)).orElseThrow(() -> new NotFoundException("해당 유저가 존재하지 않습니다.")));

		ArrayList<ArrayList<Consumption>> monthlyAllConsumption = getMonthlyConsumption(records, date);
		ArrayList<String> categoryList = monthlyTargetAmountService.getMonthlyTargetAmountByCategory(userId).getCategories();

		ArrayList<MonthlyConsumptionsAndTargetsByCategoryResponse.MonthlyConsumptionsAndTargetsByCategory> response =
			getMonthlyConumptionsAndTargetsByCategory(monthlyAllConsumption, categoryList);


		return MonthlyConsumptionsAndTargetsByCategoryResponse.builder()
			.userIncome(userRepository.findById(userId).orElseThrow(()-> new NotFoundException("유저 정보가 없습니다.")).getIncome())
			.monthlyConsumptionsAndTargetsByCategory(response)
			.build();

	}

	private ArrayList<MonthlyConsumptionsAndTargetsByCategoryResponse.MonthlyConsumptionsAndTargetsByCategory> getMonthlyConumptionsAndTargetsByCategory(ArrayList<ArrayList<Consumption>> monthlyAllConsumption, ArrayList<String> categoryList) {
		ArrayList<MonthlyConsumptionsAndTargetsByCategoryResponse.MonthlyConsumptionsAndTargetsByCategory> response = new ArrayList<>();
		for(String category : categoryList) {
			int categorySum = 0;
			int target = 0;
			for(ArrayList<Consumption> consumptions : monthlyAllConsumption) {
				for(Consumption consumption : consumptions) {
					if(consumption.getCategory().equals(category)) {
						categorySum += consumption.getAmount();
						target = consumption.getTargetAmount();
					}
				}
			}
			response.add(MonthlyConsumptionsAndTargetsByCategoryResponse.MonthlyConsumptionsAndTargetsByCategory.from(category, categorySum, target));   // target은 아직 구현 안함
		}
		return response;
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


