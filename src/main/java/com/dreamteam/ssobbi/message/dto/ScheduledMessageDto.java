package com.dreamteam.ssobbi.message.dto;

import com.dreamteam.ssobbi.message.entity.ScheduledMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ScheduledMessageDto {
	private Long id;

	private String message;
	private LocalDateTime scheduledTime;    // user option the time.
	private String recipient;	// user's nickname? maybe..

	public ScheduledMessageDto from(ScheduledMessage smg){
		return new ScheduledMessageDto(smg.getId(), smg.getMessage(), smg.getScheduledTime(), smg.getRecipient());
	}
}
