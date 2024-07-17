package com.dreamteam.ssobbi.message.controller;

import com.dreamteam.ssobbi.message.controller.reuqest.ScheduleMessageRequest;
import com.dreamteam.ssobbi.message.entity.ScheduledMessage;
import com.dreamteam.ssobbi.message.service.ScheduledMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ssobbi")
public class ScheduledMessageController {

	private final ScheduledMessageService scheduledMessageService;

	@PostMapping("/message")
	public ScheduledMessage scheduleMessage(@RequestBody ScheduleMessageRequest request) {
		return scheduledMessageService.scheduleMessage(request.getMessage(), request.getScheduledTime(), request.getRecipient());
	}

	@GetMapping("/message")
	public List<ScheduledMessage> getScheduledMessages() {
		return scheduledMessageService.getScheduledMessages();
	}
}
