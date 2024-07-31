package com.dreamteam.ssobbi.record.controller.response;

import lombok.*;

import java.util.ArrayList;

@Data
@Builder
public class MonthlyConsumptionsAndTargetsByCategoryResponse {

	int userIncome;
	private ArrayList<MonthlyConsumptionsAndTargetsByCategory> monthlyConsumptionsAndTargetsByCategory;

	public void add(MonthlyConsumptionsAndTargetsByCategory monthlyConsumptionsAndTargetsByCategory) {
		if(this.monthlyConsumptionsAndTargetsByCategory == null){
			this.monthlyConsumptionsAndTargetsByCategory = new ArrayList<>();
		}
		this.monthlyConsumptionsAndTargetsByCategory.add(monthlyConsumptionsAndTargetsByCategory);
	}

	@Getter
	@Builder
	public static class MonthlyConsumptionsAndTargetsByCategory {
		private String category;
		private Integer consumption;
		private Integer target;

		public static MonthlyConsumptionsAndTargetsByCategory from(String category, Integer consumption, Integer target) {
			return MonthlyConsumptionsAndTargetsByCategory.builder().category(category).consumption(consumption).target(target).build();
		}
	}

}
