package com.dreamteam.ssobbi.user.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@Builder
public class AboutUserCategoryConsumptionResponse {

	private ArrayList<userCategoryAndAmount> userCategoryAndAmounts;

	@Data
	@AllArgsConstructor
	public static class userCategoryAndAmount {
		private String name;
		private int consumption;

		public void AboutUserCategoryConsumptionResponse(String name, int consumption) {
			this.name = name;
			this.consumption = consumption;
		}
	}
}
