package com.dreamteam.ssobbi.user.controller.response;

import lombok.Data;

@Data
public class UpdateUserInfoAboutPhone {
	private String name;
	private String phoneNumber;

	public UpdateUserInfoAboutPhone(String name, String phoneNumber) {
		this.name = name;
		this.phoneNumber = phoneNumber;
	}
}
