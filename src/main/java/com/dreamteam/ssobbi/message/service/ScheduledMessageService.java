package com.dreamteam.ssobbi.message.service;

import com.dreamteam.ssobbi.message.dto.ScheduledMessageDto;
import com.dreamteam.ssobbi.message.entity.ScheduledMessage;
import com.dreamteam.ssobbi.message.repository.ScheduledMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduledMessageService {

	private final ScheduledMessageRepository scheduledMessageRepository;

	public ScheduledMessage scheduleMessage(String message, LocalDateTime scheduledTime, String recipient) {

		ScheduledMessageDto scheduledMessageDto = ScheduledMessageDto.builder().message(message).scheduledTime(scheduledTime).recipient(recipient).build();

		ScheduledMessage scheduledMessage = ScheduledMessage.from(scheduledMessageDto);

		return scheduledMessageRepository.save(scheduledMessage);
	}

	// do we need this method?
	public List<ScheduledMessage> getScheduledMessages() {
		return scheduledMessageRepository.findAll();
	}
}
