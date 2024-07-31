package com.dreamteam.ssobbi.record.service;

import com.dreamteam.ssobbi.monthlyTargetAmount.entity.MonthlyTargetAmount;
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

  	public MonthlyConsumptionsAndTargetsByCategoryResponse getMonthlyCategoryConsumption(Long userId) {
//  public ArrayList<ArrayList<Consumption>> getMonthlyCategoryConsumption(Long userId) {

    // 현재 로그인한 유저를 통해 기록에 들어가
    // 들어간 기록을 통해서 기록별 사용 금액에 들어가.
    // 기록별 사용 금액을 통해 카테고리별로 사용한 금액을 더해준다.
    // 그런 다음, 카테고리와 사용 금액, target 금액을 모두 객체화해서 response에 담는다.

    ArrayList<Record> records =
        recordRepository.findByUser((userRepository.findById(userId)).orElseThrow()); // 예외처리 필요

    ArrayList<ArrayList<Consumption>> allConsumption = new ArrayList<>();
    for (Record record : records) {
      if (record.getDate().getMonth() == LocalDate.now().getMonth()) {
        allConsumption.add(consumptionRepository.findByRecord(record));
      }
    }

    // 카테고리별로 사용한 금액을 더해준다.
    // 월별 소비 내역에서 카테고리를 비교해서 동일하면, 사용 금액을 더해준다.

    // 일단 카테고리를 가져와야겠지.
    ArrayList<String> categoryList = monthlyTargetAmountService.getMonthlyTargetAmountByCategory(userId).getCategories();

    // 가져온 곳에서 consumptions와 비교해서 같은 카테고리면 사용 금액을 더해준다.
    ArrayList<MonthlyConsumptionsAndTargetsByCategoryResponse.MonthlyConsumptionsAndTargetsByCategory> response = new ArrayList<>();
    for(String category : categoryList) {
    	int categorySum = 0;
		int target = 0;
    	for(ArrayList<Consumption> consumptions : allConsumption) {
    		for(Consumption consumption : consumptions) {
    			if(consumption.getCategory().equals(category)) {
    				categorySum += consumption.getAmount();
					target = consumption.getTargetAmount();
    			}
    		}
    	}
    	response.add(MonthlyConsumptionsAndTargetsByCategoryResponse.MonthlyConsumptionsAndTargetsByCategory.from(category, categorySum, target));   // target은 아직 구현 안함
    }

    return MonthlyConsumptionsAndTargetsByCategoryResponse.builder().monthlyConsumptionsAndTargetsByCategory(response).build();
  }
}

