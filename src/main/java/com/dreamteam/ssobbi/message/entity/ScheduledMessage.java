package com.dreamteam.ssobbi.message.entity;

import com.dreamteam.ssobbi.message.dto.ScheduledMessageDto;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class ScheduledMessage {

//	모든 유저가 21시에 알람 메세지를 받도록.

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String message;
	private LocalDateTime scheduledTime;    // user option the time.
	private String recipient;	// user's nickname..? maybe..

	protected ScheduledMessage() {}
	private ScheduledMessage(String message, LocalDateTime scheduledTime, String recipient) {
		this.message = message;
		this.scheduledTime = scheduledTime;
		this.recipient = recipient;
	}

	public static ScheduledMessage from(ScheduledMessageDto dto){
		return new ScheduledMessage(dto.getMessage(), dto.getScheduledTime(), dto.getRecipient());
	}

}
