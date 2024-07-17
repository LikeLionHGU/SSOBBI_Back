package com.dreamteam.ssobbi.message.controller.reuqest;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleMessageRequest {
	private String message;
	private LocalDateTime scheduledTime;
	private String recipient;

}
