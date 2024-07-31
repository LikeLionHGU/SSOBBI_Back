package com.dreamteam.ssobbi.user.controller.response;

import lombok.Data;

@Data
public class UpdateUserInfoAboutPhoneResponse {
	private String name;
	private String phoneNumber;

	public UpdateUserInfoAboutPhoneResponse(String name, String phoneNumber) {
		this.name = name;
		this.phoneNumber = phoneNumber;
	}
}
